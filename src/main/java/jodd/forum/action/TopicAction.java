package jodd.forum.action;

import jodd.forum.model.Topic;
import jodd.forum.service.TopicService;
import jodd.madvoc.meta.Action;
import jodd.madvoc.meta.MadvocAction;
import jodd.madvoc.meta.Out;
import jodd.petite.meta.PetiteInject;

import java.util.List;

@MadvocAction
public class TopicAction {

    @PetiteInject
    TopicService topicService;

    @Out
    List<Topic> topicList;
    @Out
    List<String> imageList;

    @Action("/listTopic.do")
    public String listTopic(){
        topicList = topicService.listTopic();
        System.out.println(topicList);
        return "topic";
    }

    @Action("/listImage.do")
    public String listImage(){
        imageList = topicService.listImage();
        return "image";
    }
}
