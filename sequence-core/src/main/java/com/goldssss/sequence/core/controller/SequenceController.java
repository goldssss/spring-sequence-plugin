package com.goldssss.sequence.core.controller;

import com.goldssss.sequence.core.cache.SequenceCache;
import com.goldssss.sequence.core.cache.SequencePageTemplage;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/sequence")
public class SequenceController {

    @PostMapping("/list")
    public Set<String> list(){
        return SequenceCache.sequenceContextTreeMap.keySet();
    }

    @RequestMapping("/sequence")
    public String sequence(){
        StringBuffer sb = new StringBuffer();
        sb.append("<div>");
        for (String key : SequenceCache.sequenceContextTreeMap.keySet()){
            sb.append("<a").append(" target=\"_blank\"").append(" href=\"").append("/sequence/")
                    .append(SequenceCache.sequenceContextTreeMap.get(key).getClazzName())
                    .append("/").append(SequenceCache.sequenceContextTreeMap.get(key).getMethodName())
                    .append("(").append(SequenceCache.sequenceContextTreeMap.get(key).getParamsTypes()).append(")")
                    .append("\">").append(key).append("</a>").append("</br>");
        }
        sb.append("</div>");
        return sb.toString();
    }

    @RequestMapping("/sequence/{clazz}/{method}")
    public String sequenceContext(@PathVariable("clazz") String clazz,@PathVariable("method") String method){
        StringBuffer context = new StringBuffer();
        StringBuffer title = new StringBuffer(clazz).append(".").append(method);
        context.append(SequenceCache.sequenceContextTreeMap.get(title.toString()).getMessageContext());
        String result = new String(SequencePageTemplage.SEQUENCE_PAGE_CONTEXT);
        return result.replace("${title}",title.toString()).replace("${sequenceContext}",context);
    }
}
