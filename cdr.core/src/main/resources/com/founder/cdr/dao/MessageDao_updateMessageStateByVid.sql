/**
 * 执行完当前队列后，重置正在运行的消息，下次batch启动时运行。
 * @param vid v3消息ID
 * @return 
 */
  UPDATE MESSAGE a
     SET a.state = '0'
   WHERE a.vid = /*messageId*/