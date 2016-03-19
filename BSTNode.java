/**
     * Inner (non-static) class to represent a node in the tree. Each node
     * includes a String and an integer count. The class is protected so that it
     * may be accessed by subclasses of BSTCounter.
     */
    public class BSTNode<E> {
        /**
         * The left child of this node.
         */
        public BSTNode left;

        /**
         * The right child of this node.
         */
        public BSTNode right;

        /**
         * The data element stored at this node.
         */
        public E data;

        /**
         * The count for this data element.
         */
        public int count;

        /**
         * Create a new data node. Also takes care of incrementing the tree
         * size.
         *
         * @param data data element to be stored at this node.
         */
        public BSTNode(E data) {
            this.data = data;
            count = 1;
            left = right = null;
        }
    }