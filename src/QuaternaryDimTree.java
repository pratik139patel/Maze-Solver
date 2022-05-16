package src;
import java.awt.Dimension;

public class QuaternaryDimTree 
{
    Node RootNode = null;

    public QuaternaryDimTree(final Dimension root_dim, final Dimension top_dim, final Dimension left_dim, final Dimension bottom_dim, final Dimension right_dim) 
        { RootNode = new Node(root_dim, top_dim, left_dim, bottom_dim, right_dim); }
    
    public void addNode(final Dimension center_dim, final Dimension top_dim, final Dimension left_dim, final Dimension bottom_dim, final Dimension right_dim)
        { RootNode = new Node(center_dim, top_dim, left_dim, bottom_dim, right_dim); }
}
