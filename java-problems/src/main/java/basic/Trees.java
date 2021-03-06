package basic;

/**
 * Author by darcy
 * Date on 17-9-14 下午3:01.
 * Description:
 *
 * 1. 节点的度: 节点所拥有的子树(孩子)的个数称为该节点的度.
 * 2. 叶子节点: 度为0的节点.
 * 3. 节点的层次: 根节点的层次为1. 其余节点的层次为父节点的层次+1.
 * 4. 树的深度: 所有节点的最大层数称为树的深度.
 * 5. 树的度: 树中各个节点度的最大值称为树的度.叶子节点的度为0
 * 6. 满二叉树: 除叶子结点外的所有结点均有两个子结点。节点数达到最大值。所有叶子结点必须在同一层上.
 * 7. 完全二叉树: 若一棵二叉树至多只有最下面的两层上的结点的度数可以小于2，并且最下层上的结点都集中
 *    在该层最左边的若干位置上，则此二叉树成为完全二叉树。
 * 8. 对于一棵非空的二叉树, 度为0的节点(叶子节点)总比度为2的节点多一个.即n0 = n2 + 1
 *    证明: 总个数= n0 + n1 + n2, 而二叉树的性质n = n1 + 2 * n2 + 1, 所以n2 = n0 + 1
 * 9. 具有n个节点的完全二叉树的深度为　log2n + 1
 * 10. 完全二叉树上有1001个节点.其中叶子节点的个数是501个
 *    证明：　n = n0 + n1 + n2 = n0 + n1 + (n0 - 1) = 2 * n0 + n1 - 1 = 1001. (n1 = 1 或者 0)
 * 11. 具有1000个节点的树中, 其边的个数是99: 因为每个节点一条入边.
 *
 */
public class Trees {




}
