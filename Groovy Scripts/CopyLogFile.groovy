//Package euroClearutils

import com.jcraft.jsch.*
import java.lang.*
import java.text.SimpleDateFormat
class CopyLogFile
{
   def FileTransfer(cmd, uId, svr, fileF, fileT) {   // File Transfer function.  Purpose is to execute a file transfer using the input parameters.
      def sout = new StringBuffer()
      def serr = new StringBuffer()
      /*log.info "CommandHarness: " + cmd
      log.info "UserId: " + uId
      log.info "Server: " + svr
      log.info "FileFrom: " + fileF
      log.info "FileTo: " + fileT */
      def cmdExecute = [cmd, uId, svr, fileF, fileT] //
     // log.info "cmdExecute: " + cmdExecute
      Process x = cmdExecute.execute()   //
      x.consumeProcessOutput(sout, serr)    //  #### IMPORTANT #### This line must be here or the file transfer using PSFTP on Windows will not work
      x.waitFor()
    //  log.info "x=${x.text}"
    //  log.info 'sout: ' + sout
    //  log.info 'serr: ' + serr
      return "code: ${ x.exitValue()}"
   }

   Session session
   Channel channel
   ChannelSftp channelSftp
   JSch jsch

   def Connect(String hostName, String userName, String passCode) {
         jsch = new JSch()
         session = jsch.getSession( userName, hostName, 22 )
         session.setPassword(passCode)
         session.setTimeout(3000)
         java.util.Properties config = new java.util.Properties()
         config.put("StrictHostKeyChecking", "no")
         session.setConfig(config)
         session.connect()
         if (session.IsConnected) {
            channel = session.openChannel("sftp")
            channel.connect()
            channelSftp = channel as ChannelSftp
            //log.info 'Connected to: ' + hostName
            //return session
         }
   }

   def ChangeDirectory( String remotePath ) {
      try {
         //log.info 'Path before ChangeDirectory is: ' + getPath()
         channelSftp.cd( remotePath )
         //log.info 'Path after ChangeDirectory is: ' + getPath()
      }
      catch( Exception e ) {
         disconnect()
         log.error e.toString()
      }
   }

   def Disconnect() {
      channelSftp.quit()
      channelSftp.disconnect()
      channel.disconnect()
      session.disconnect()
      //log.info "Disconnected"
   };

   def IsConnected() {
      return session == null ? false : session.isConnected()
   };

   def getPath() {
   return channelSftp.pwd()+"/"
   }

   def getList(String remotePath) {
   osList = channelSftp.lstat(remotePath)+"/"
      return osList
   }

   def GetStat(String remotePath) {
      return channelSftp.lstat(remotePath)
   }

   def GetFile(String rFQFN, String lFQFN) {
      //log.info "Fully qualified file name on remote server: $rFQFN"
      //log.info "Fully qualified file name on local computer:  $lFQFN"
      channelSftp.get(rFQFN, lFQFN)
   }

}
