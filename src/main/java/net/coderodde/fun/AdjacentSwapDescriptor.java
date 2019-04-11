package net.coderodde.fun;

/**
 * Encodes an adjacent pair of elements in an array.
 */
public final class AdjacentSwapDescriptor {

    /**
     * The index of the left list element. We imply here, that the index of
     * the right list element is {@code startingIndex + 1}.
     */
    public final int startingIndex;

    /**
     * Constructs a new, immutable descriptor.
     * 
     * @param startingIndex the index of the left list element.
     */
    public AdjacentSwapDescriptor(int startingIndex) {
        this.startingIndex = startingIndex;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!o.getClass().equals(this.getClass())) {
            return false;
        }

        AdjacentSwapDescriptor other = (AdjacentSwapDescriptor) o;
        return startingIndex == other.startingIndex;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public int hashCode() {
        return startingIndex;
    }

    /**
     *{@inheritDoc } 
     */
    @Override
    public String toString() {
        return "(" + startingIndex + ", " + (startingIndex + 1) + ")";
    }
}
