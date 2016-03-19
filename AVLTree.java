/**
 * BSTCounter implements the DataCounter interface using a binary search tree to
 * store the data items and counts.
 *
 * @param <E> The type of the data elements. Note that we have strengthened the
 *            constraints on E such that E is now a Comparable.
 */
public class AVLTree<E extends Comparable<? super E>> implements
        DataCounter<E> {

    /**
     * The root of the binary search tree. root is null if and only if the tree
     * is empty.
     */
    protected BSTNode overallRoot;

    /**
     * Number of nodes in the binary search tree.
     */
    protected int size;

    /**
     * Inner (non-static) class to represent a node in the tree. Each node
     * includes a String and an integer count. The class is protected so that it
     * may be accessed by subclasses of BSTCounter.
     */
    protected class BSTNode {
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
            size++;
        }
    }

    /**
     * Create an empty binary search tree.
     */
    public AVLTree() {
        overallRoot = null;
        size = 0;
    }

	/**
	 * Essentially the same as the incCount method found
	 * in the BinarySearchTree given to us. The only
	 * change is that instead of creating a new node
	 * once a null spot is found, it goes straight
	 * into the insert method
	 */
    public void incCount(E data) {
        if (overallRoot == null) {
            overallRoot = new BSTNode(data);
        } else {
            // traverse the tree
            BSTNode currentNode = overallRoot;
            while (true) {
                int cmp = data.compareTo(currentNode.data);

                if (cmp == 0) {
                    currentNode.count++;
                    return;
                } else if (cmp < 0) {
                    if (currentNode.left == null) {
//                        currentNode.left = new BSTNode(data);
                    	insert(data);
                        return;
                    }
                    currentNode = currentNode.left;
                } else {
                    if (currentNode.right == null) {
//                        currentNode.right = new BSTNode(data);
                    	insert(data);
                        return;
                    }
                    currentNode = currentNode.right;
                }
            }
        }
    }

    /**
     * Searches for and returns the node containing given data.
     */
	public DataCount<E> search(E word) {
		BSTNode node = search(overallRoot, word);
		DataCount<E> result = null;
		if(node != null) {
			result = new DataCount(word, node.count); 
		}
		return result;
	}
	
    public BSTNode search(BSTNode node, E data) {

        if (node == null)
        	return null;
        int cmp = data.compareTo(node.data);
        if (cmp < 0)
        	return search(node.left, data);
        else if (cmp > 0)
        	return search(node.right, data);
        else
        	return node;
    }
    
    /** {@inheritDoc} */
    public int getSize() {
        return size;
    }

    /** {@inheritDoc} */
    public DataCount<E>[] getCounts() {
    	@SuppressWarnings("unchecked")
        DataCount<E>[] counts = new DataCount[size];
        if (overallRoot != null)
            traverse(overallRoot, counts, 0);
        return counts;
    }

    /**
     * Do an inorder traversal of the tree, filling in an array of DataCount
     * objects with the count of each element. Doing an inorder traversal
     * guarantees that the result will be sorted by element. We fill in some
     * contiguous block of array elements, starting at index, and return the
     * next available index in the array.
     *
     * @param counts The array to populate.
     */
    protected int traverse(BSTNode root, DataCount<E>[] counts, int idx) {
        if(root != null) {
            idx = traverse(root.left, counts, idx);
            counts[idx] = new DataCount<E>(root.data, root.count);
            idx = traverse(root.right, counts, idx + 1);
        }
        return idx;
    }

    /**
     * Dump the contents of the tree to a String (provided for debugging and
     * unit testing purposes).
     *
     * @return a textual representation of the tree.
     */
    protected String dump() {
        if (overallRoot != null)
            return dump(overallRoot);
        return "<empty tree>";
    }

    /**
     * Dump the contents of the subtree rooted at this node to a String
     * (provided for debugging purposes).
     *
     * @return a textual representation of the subtree rooted at this node.
     */
    protected String dump(BSTNode root) {
        if(root == null)
            return ".";

        String out = "([" + root.data + "," + root.count + "] ";
        out += dump(root.left);
        out += " ";
        out += dump(root.right);
        out += ")";

        return out;
    }
    
    /**
     * @return the height of the tree from the given node.
     */
    private int height(BSTNode x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }
    
    /**
     * Inserts a node into the AVL Tree.
     */
    public void insert( E x )
    {
        overallRoot = insert( x, overallRoot );
    }
    private BSTNode insert( E x,BSTNode t )
    {
        if( t == null )
            return new BSTNode( x );
        
        int compareResult = x.compareTo( t.data );
        
        if( compareResult < 0 )
            t.left = insert( x, t.left );
        else if( compareResult >= 0 )
            t.right = insert( x, t.right );
        return balance( t );
    }
    
    /**
     * Checks whether the tree is balanced, and if not,
     * balances the tree using the rotation methods
     */
    private BSTNode balance( BSTNode t )
    {
        if( t == null )
            return t;
        
        if( height( t.left ) - height( t.right ) > 1 )
            if( height( t.left.left ) >= height( t.left.right ) )
                t = rotateWithLeftChild( t );
            else
                t = doubleWithLeftChild( t );
        else
        if( height( t.right ) - height( t.left ) > 1 )
            if( height( t.right.right ) >= height( t.right.left ) )
                t = rotateWithRightChild( t );
            else
                t = doubleWithRightChild( t );

        return t;
    }
    
    /**
     * Rotation methods for balancing the tree.
     */
    private BSTNode rotateWithLeftChild( BSTNode k2 )
    {
        BSTNode k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        return k1;
    }
    private BSTNode rotateWithRightChild( BSTNode k1 )
    {
        BSTNode k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        return k2;
    }
    private BSTNode doubleWithLeftChild( BSTNode k3 )
    {
        k3.left = rotateWithRightChild( k3.left );
        return rotateWithLeftChild( k3 );
    }
    private BSTNode doubleWithRightChild( BSTNode k1 )
    {
        k1.right = rotateWithLeftChild( k1.right );
        return rotateWithRightChild( k1 );
    }
}
