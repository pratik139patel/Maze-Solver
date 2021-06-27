import java.awt.Dimension;

public class Node 
{
    public Dimension Center = null, Top = null, Left = null, Bottom = null, Right = null;

    public Node(final Dimension C, final Dimension T, final Dimension L, final Dimension B, final Dimension R) { Center = C; Top = T; Left = L; Bottom = B; Right = R; }

    public Dimension getCenter() {return Center;}
    public Dimension getTop() {return Top;}
    public Dimension getLeft() {return Left;}
    public Dimension getBottom() {return Bottom;}
    public Dimension getRight() {return Right;}

    public void setCenter(final Dimension D) {Center = D;}
    public void setTop(final Dimension D) {Top = D;}
    public void setLeft(final Dimension D) {Left = D;}
    public void setBottom(final Dimension D) {Bottom = D;}
    public void setRight(final Dimension D) {Right = D;}

    public boolean compareNode(final Node passed_node) { return (Center == passed_node.Center && Top == passed_node.Top && Left == passed_node.Left && Bottom == passed_node.Bottom && Right == passed_node.Right) ? (true) : (false); }
}
