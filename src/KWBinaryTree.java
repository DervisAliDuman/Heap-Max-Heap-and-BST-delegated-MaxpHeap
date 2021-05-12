
import java.io.*;

public class KWBinaryTree<E> implements Serializable{
	protected Node<E> root;
	
	class Node<E> implements Serializable{
        protected E data;
        protected Node<E> left;
        protected Node<E> right;
		public Node(E data){
			this.data = data;
			left = null;
			right = null;
		}

        public Node<E> getLeft() {
            return left;
        }

        public Node<E> getRight() {
            return right;
        }

        public E getData() {
            return data;
        }

        public String toString() {
			return data.toString();
		}
	}
	
	public KWBinaryTree() {
        root = null;
    }
	
	protected KWBinaryTree(Node<E> root) {
        this.root = root;
    }
	
	public KWBinaryTree(E data, KWBinaryTree<E> leftTree, KWBinaryTree<E> rightTree) {
        root = new Node<E>(data);
        if (leftTree != null) {
            root.left = leftTree.root;
        } else {
            root.left = null;
        }
        if (rightTree != null) {
            root.right = rightTree.root;
        } else {
            root.right = null;
        }
    }

    public KWBinaryTree<E> getLeftSubtree() {
        if (root != null && root.left != null) {
            return new KWBinaryTree<E>(root.left);
        } else {
            return null;
        }
    }
	
	public KWBinaryTree<E> getRightSubtree() {
        if (root != null && root.right != null) {
            return new KWBinaryTree<E>(root.right);
        } else {
            return null;
        }
    }
	
	public boolean isLeaf() {
        return (root.left == null && root.right == null);
    }
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
        sb.append("Root : Level " + (1) + ": ");
        preOrderTraverse(root, 1, sb);
        return sb.toString();
	}
	
	//////////BUNA BAK
	private void preOrderTraverse(Node<E> node, int depth, StringBuilder sb) {
        for (int i = 1; i < depth; i++) {
            sb.append("  ");
        }
        if (node == null) {
            sb.append("null\n");
        } else {
            sb.append(node.toString());
            sb.append("\n");
            sb.append("Left : Level " + (depth+1) + ": ");
            preOrderTraverse(node.left, depth + 1, sb);
            sb.append("Right: Level " + (depth+1) + ": ");
            preOrderTraverse(node.right, depth + 1, sb);
        }
    }
	
	
	/*public static DADBinaryTree<String> readBinaryTree(Scanner scan) {
		String data = scan.next();
		if (data.equals("null")) {
			return null;
		}else {
			DADBinaryTree<String> leftTree = readBinaryTree(scan);
			DADBinaryTree<String> rightTree = readBinaryTree(scan);
			return new DADBinaryTree<String>(data, leftTree,rightTree);
		}
		
    }*/
	
	public static KWBinaryTree < String > readBinaryTree(BufferedReader bR) throws IOException {
		// Read a line and trim leading and trailing spaces.
		String data = bR.readLine().trim();
	  	if (data.equals("null")) {
		  return null;
	  	}
	  	else {
            KWBinaryTree < String > leftTree = readBinaryTree(bR);
            KWBinaryTree < String > rightTree = readBinaryTree(bR);
	    	return new KWBinaryTree < String > (data, leftTree, rightTree);
	  	}
	}
	
	public E getData() {
		if (root != null) {
			return root.data;
		}
		return null;
	}
}
