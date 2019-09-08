package jodd.forum.service;

import jodd.forum.mapper.TopicMapper;
import jodd.forum.model.Topic;
import jodd.jtx.meta.ReadWriteTransaction;
import jodd.petite.meta.PetiteBean;
import jodd.petite.meta.PetiteInject;

import java.util.List;

@PetiteBean
public class TopicService {

    @PetiteInject
    TopicMapper topicMapper;

    @ReadWriteTransaction
    public List<Topic> listTopic() {
        return topicMapper.listTopic();
    }
    @ReadWriteTransaction
    public List<String> listImage() {
        return topicMapper.listImage();
    }

}
