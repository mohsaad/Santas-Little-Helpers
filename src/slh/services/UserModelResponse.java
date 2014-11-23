package slh.services;

import java.util.List;

import com.google.common.collect.Lists;

public class UserModelResponse
{
    public String id, source;
    public TraitTreeNode tree;
    
    public static final class TraitTreeNode
    {
        String id, name;
        double percentage;
        List<TraitTreeNode> children = Lists.newArrayList();
        
        public boolean isHeading()
        {
            return children == null || children.isEmpty();
        }
    }
}
