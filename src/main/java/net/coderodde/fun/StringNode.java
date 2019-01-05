package net.coderodde.fun;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Rodion "rodde" Efremov
 * @version 1.6 (Jan 3, 2019)
 */
final class StringNode {
    
    private final String string;
    private final int sourceIndex;
    private final int targetIndex;
    private final List<StringNode> neighbors = new ArrayList<>(2);
    
    StringNode(String string,
               int sourceIndex,
               int targetIndex) {
        this.string = string;
        this.sourceIndex = sourceIndex;
        this.targetIndex = targetIndex;
    }
    
    void addNeighbor(StringNode stringNode) {
        neighbors.add(stringNode);
    }
    
    List<StringNode> getNeighbors() {
        return neighbors;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        
        if (obj == null) {
            return false;
        }
        
        StringNode other = (StringNode) obj;
        
        return other.string.equals(string) 
                && other.sourceIndex == sourceIndex 
                && other.targetIndex == targetIndex;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.string);
        hash = 17 * hash + this.sourceIndex;
        hash = 17 * hash + this.targetIndex;
        return hash;
    }
}
