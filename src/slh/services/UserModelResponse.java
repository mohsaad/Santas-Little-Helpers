package slh.services;

import java.util.List;

import com.google.common.collect.Lists;

public class UserModelResponse
{
    public String id, source;
    public TraitTreeNode tree;
    
    public static final class TraitTreeNode
    {
        public String id, name;
        public double percentage;
        public List<TraitTreeNode> children = Lists.newArrayList();
        
        public boolean isNotHeading()
        {
            return children == null || children.isEmpty();
        }
    }
}
