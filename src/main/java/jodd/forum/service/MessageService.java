package jodd.forum.service;

import jodd.forum.mapper.MessageMapper;
import jodd.forum.model.Message;
import jodd.forum.util.MyUtil;
import jodd.jtx.meta.ReadWriteTransaction;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@PetiteBean
public class MessageService {

    @PetiteInject
    MessageMapper messageMapper;

    @ReadWriteTransaction
    public Map<String, List<Message>> listMessageByUid(Integer sessionUid) {
        List<Message> messageList = messageMapper.listMessageByUid(sessionUid);
        Map<String, List<Message>> map = new HashMap<>();
        for (Message message : messageList) {
            String time = MyUtil.formatDate(message.getMsgTime()).substring(0, 11);
            if (map.get(time) == null) {
                map.put(time, new LinkedList<Message>());
                map.get(time).add(message);
            } else {
                map.get(time).add(message);
            }
        }
        return map;
    }
}
