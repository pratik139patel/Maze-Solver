import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import java.awt.Color;
import java.awt.Dimension;

public class Maze_solver 
{
    public static void main(String[] Args) { new Maze_solver(); }

    private JFrame main_frame;
    private Maze_solver()
    {
        main_frame = new JFrame("Main Menu"); main_frame.setIconImage((new ImageIcon("Pratik Patel.jpg")).getImage());
        main_frame.setLayout(new GridBagLayout()); GridBagConstraints constraints = new GridBagConstraints();

        JLabel empty_label = new JLabel(); empty_label.setPreferredSize(new Dimension(30, empty_label.getPreferredSize().height));
        constraints.insets = new Insets(5, 5, 5, 5); main_frame.add(empty_label, constraints);
        constraints.gridx = 1; main_frame.add(new JLabel("ROWS:"), constraints);
        constraints.gridx = 2; main_frame.add(new JLabel("COLS:"), constraints);

        JTextField cols_field = new JTextField(2); constraints.gridy = 1; main_frame.add(cols_field, constraints); 
        JTextField rows_field = new JTextField(2); constraints.gridx = 1; main_frame.add(rows_field, constraints);

        cols_field.addFocusListener(new FocusListener() 
        {
            @Override public void focusGained(FocusEvent e) { cols_field.selectAll(); }
            @Override public void focusLost(FocusEvent e) {}
        });

        rows_field.addFocusListener(new FocusListener() 
        {
            @Override public void focusGained(FocusEvent e) { rows_field.selectAll(); }
            @Override public void focusLost(FocusEvent e) {}
        });

        JButton create_maze_btn = new JButton("Create"); constraints.gridy = 2; create_maze_btn.setPreferredSize(new Dimension(82,create_maze_btn.getPreferredSize().height)); main_frame.add(create_maze_btn, constraints); 
        JButton exit_btn = new JButton("Exit"); constraints.gridx = 2; exit_btn.setPreferredSize(create_maze_btn.getPreferredSize()); main_frame.add(exit_btn, constraints);

        JTextField error_bar = new JTextField(); error_bar.setPreferredSize(new Dimension(255, error_bar.getPreferredSize().height)); error_bar.setBackground(Color.RED); error_bar.setEnabled(false); error_bar.setHorizontalAlignment(JTextField.HORIZONTAL);
        constraints.gridx = 0; constraints.gridy = 3; constraints.gridwidth = 4; main_frame.add(error_bar, constraints);

        create_maze_btn.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { try{Maze(Integer.parseInt(rows_field.getText()),Integer.parseInt(cols_field.getText())); error_bar.setText("");} catch(Exception ex) {error_bar.setText("ERROR: Invalid Input");} } });
        exit_btn.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { try{maze_frame.dispose();} catch(Exception ex) {} main_frame.dispose();} });

        main_frame.pack(); 
        main_frame.setVisible(true);
        main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main_frame.setLocationRelativeTo(null);
    }

    private JFrame maze_frame;
    private Color current_user_color_selection;
    private JButton[][] maze_grid_btn;
    private Color wall_color = Color.BLACK;
    private Color path_color = Color.WHITE;
    private Color stop_color = Color.GREEN;
    private Color result_color = Color.BLUE;
    private JTextField maze_frame_error_bar;

    private JButton run_btn;
    private JButton stop_btn;
    private JButton wall_btn;
    private JButton path_btn;
    private JButton reset_btn;
    private JButton shortest_path_enable_btn;

    private boolean is_btn_bg_color_enabled = true;

    private void Maze(final int num_rows, final int num_cols) throws Exception
    {
        if(num_cols < 1 || num_rows < 1) {throw new Exception();}
        else if(num_cols*num_rows < 2) {throw new Exception();}

        try {maze_frame.dispose();} catch(Exception ex) {}
        maze_frame = new JFrame("Maze Solver"); maze_frame.setIconImage(main_frame.getIconImage());
        maze_frame.setLayout(new GridBagLayout()); GridBagConstraints constraints = new GridBagConstraints();

        current_user_color_selection = path_color; shortest_path_enabled = false;

        maze_grid_btn = new JButton[num_rows][num_cols]; Dimension preferred_dim = new Dimension(30,30);
        for(int i = 0; i < maze_grid_btn.length; ++i) 
        {
            constraints.gridy = i;
            for(int j = 0; j < maze_grid_btn[0].length; ++j)
            {
                if(i == 0)
                {
                    if(j == 0) { constraints.insets = new Insets(20,20,0,0); }
                    else if(j == num_cols-1) { constraints.insets = new Insets(20,0,0,20); }
                    else { constraints.insets = new Insets(20,0,0,0); }
                }
                else if(i == num_rows-1)
                {
                    if(j == 0) { constraints.insets = new Insets(0,20,20,0); }
                    else if(j == num_cols-1) {constraints.insets = new Insets(0,0,20,20);}
                    else { constraints.insets = new Insets(0,0,20,0); }
                }
                else if(j == 0) { constraints.insets = new Insets(0,20,0,0); }
                else if(j == num_cols-1) { constraints.insets = new Insets(0,0,0,20); }
                else { constraints.insets = new Insets(0,0,0,0); }

                constraints.gridx = j+1;

                JButton temp_template_btn = new JButton();
                temp_template_btn.setPreferredSize(preferred_dim);
                temp_template_btn.setBackground(current_user_color_selection);
                temp_template_btn.addActionListener(new ActionListener() { @Override public void actionPerformed(ActionEvent e) { if(is_btn_bg_color_enabled) {temp_template_btn.setBackground(current_user_color_selection);} } });

                maze_grid_btn[i][j] = temp_template_btn;
                maze_frame.add(maze_grid_btn[i][j], constraints);
            }
        }
        
        maze_frame_error_bar = new JTextField(); maze_frame_error_bar.setBackground(Color.RED); maze_frame_error_bar.setEnabled(false); maze_frame_error_bar.setHorizontalAlignment(JTextField.CENTER); constraints.insets = new Insets(10,10,10,10);
        constraints.gridy = num_rows+1; constraints.gridx = 1; constraints.gridwidth = num_cols; maze_frame_error_bar.setPreferredSize(new Dimension(preferred_dim.width*num_cols, maze_frame_error_bar.getPreferredSize().height)); maze_frame.add(maze_frame_error_bar, constraints);

        JMenuBar maze_frame_menu_bar = new JMenuBar();
        stop_btn = new JButton("STOP"); stop_btn.setBackground(stop_color); stop_btn.addActionListener(new ActionListener(){ @Override public void actionPerformed(ActionEvent e) { current_user_color_selection = stop_color; } });
        wall_btn = new JButton("WALL"); wall_btn.setForeground(Color.WHITE); wall_btn.setBackground(wall_color); wall_btn.addActionListener(new ActionListener(){ @Override public void actionPerformed(ActionEvent e) { current_user_color_selection = wall_color; } });
        path_btn = new JButton("PATH"); path_btn.setBackground(path_color); path_btn.addActionListener(new ActionListener(){ @Override public void actionPerformed(ActionEvent e) { current_user_color_selection = path_color; } });
        run_btn = new JButton("RUN"); run_btn.addActionListener(new ActionListener(){ @Override public void actionPerformed(ActionEvent e) { is_btn_bg_color_enabled = false; run_btn.setEnabled(false); stop_btn.setEnabled(false); wall_btn.setEnabled(false); path_btn.setEnabled(false); reset_btn.setEnabled(false); shortest_path_enable_btn.setEnabled(false); maze_frame_error_bar.setText("CALCULATING...."); SolveMaze(); } });
        JButton exit_btn = new JButton("EXIT"); exit_btn.addActionListener(new ActionListener(){ @Override public void actionPerformed(ActionEvent e) { maze_frame.dispose(); } });
        reset_btn = new JButton("RESET"); reset_btn.addActionListener(new ActionListener(){ @Override public void actionPerformed(ActionEvent e) { for(int i = 0; i < maze_grid_btn.length; ++i) { for(int j = 0; j < maze_grid_btn[0].length; ++j) { current_user_color_selection = path_color; maze_grid_btn[i][j].setBackground(current_user_color_selection);} } maze_frame_error_bar.setText(""); } });
        shortest_path_enable_btn = new JButton((shortest_path_enabled)?("SHORT PATH"):("FIRST PATH")); shortest_path_enable_btn.addActionListener(new ActionListener(){ @Override public void actionPerformed(ActionEvent e) { shortest_path_enabled = !shortest_path_enabled; shortest_path_enable_btn.setText((shortest_path_enabled)?("SHORT PATH"):("FIRST PATH")); } });

        maze_frame_menu_bar.add(stop_btn); maze_frame_menu_bar.add(wall_btn); maze_frame_menu_bar.add(path_btn); maze_frame_menu_bar.add(run_btn); maze_frame_menu_bar.add(exit_btn); maze_frame_menu_bar.add(reset_btn); maze_frame_menu_bar.add(shortest_path_enable_btn);
        
        maze_frame.setJMenuBar(maze_frame_menu_bar);
        maze_frame.pack(); 
        maze_frame.setVisible(true);
        maze_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        maze_frame.setLocationRelativeTo(null);
    }
 
    private ArrayList<Dimension> stop_btn_location;
    private int count;
    private Timer timer;
    private boolean shortest_path_enabled;
    private void SolveMaze()
    {
        stop_btn_location = new ArrayList<Dimension>();
        for(int i = 0; i < maze_grid_btn.length; ++i) { for(int j = 0; j < maze_grid_btn[0].length; ++j) { if(maze_grid_btn[i][j].getBackground() == stop_color) {stop_btn_location.add(new Dimension(j,i));} else if(maze_grid_btn[i][j].getBackground() == result_color) {maze_grid_btn[i][j].setBackground(path_color);} } }
        if(stop_btn_location.size() < 2) { maze_frame_error_bar.setText("ERROR: AT LEAST 2 STOPS NEEDED!!!"); return;}

        Path = new ArrayList<Dimension>(); ShortestPath= new ArrayList<Dimension>(); search_flag = true;
        if(stop_btn_location.get(0).width <= stop_btn_location.get(stop_btn_location.size()-1).width && stop_btn_location.get(0).height >= stop_btn_location.get(stop_btn_location.size()-1).height) {first = 1; second = 2; third = 3; forth = 0;}
        else if(stop_btn_location.get(0).width > stop_btn_location.get(stop_btn_location.size()-1).width && stop_btn_location.get(0).height > stop_btn_location.get(stop_btn_location.size()-1).height) {first = 0; second = 1; third = 2; forth = 3;}
        else if(stop_btn_location.get(0).width >= stop_btn_location.get(stop_btn_location.size()-1).width && stop_btn_location.get(0).height <= stop_btn_location.get(stop_btn_location.size()-1).height) {first = 3; second = 0; third = 1; forth = 2;}
        else if(stop_btn_location.get(0).width < stop_btn_location.get(stop_btn_location.size()-1).width && stop_btn_location.get(0).height < stop_btn_location.get(stop_btn_location.size()-1).height) {first = 2; second = 3; third = 0; forth = 1;}

        count = 0;
        if(shortest_path_enabled)
        {
            if(!idealPath()) {generate_shortest_path(stop_btn_location.get(0));}
            timer = new Timer(100, new ActionListener()
            {
                @Override public void actionPerformed(ActionEvent e) 
                {                    
                    if(maze_grid_btn[ShortestPath.get(count).height][ShortestPath.get(count).width].getBackground() != stop_color) {maze_grid_btn[ShortestPath.get(count).height][ShortestPath.get(count).width].setBackground(result_color);}
                    ++count; if(count == ShortestPath.size()) { timer.stop(); run_btn.setEnabled(true); stop_btn.setEnabled(true); wall_btn.setEnabled(true); path_btn.setEnabled(true); reset_btn.setEnabled(true); shortest_path_enable_btn.setEnabled(true); is_btn_bg_color_enabled = true; }
                }
            });
            
            if(ShortestPath.size() > 0) { maze_frame_error_bar.setText("DONE!"); timer.start(); }
            else {maze_frame_error_bar.setText("COULDN'T FIND ANY PATH"); run_btn.setEnabled(true); stop_btn.setEnabled(true); wall_btn.setEnabled(true); path_btn.setEnabled(true); reset_btn.setEnabled(true); shortest_path_enable_btn.setEnabled(true); is_btn_bg_color_enabled = true; }
        }
        else
        {
            if(!idealPath()) {generate_path(stop_btn_location.get(0));}
            timer = new Timer(100, new ActionListener()
            {
                @Override public void actionPerformed(ActionEvent e) 
                {
                    if(maze_grid_btn[Path.get(count).height][Path.get(count).width].getBackground() != stop_color) {maze_grid_btn[Path.get(count).height][Path.get(count).width].setBackground(result_color);}
                    ++count; if(count == Path.size()) { timer.stop(); run_btn.setEnabled(true); stop_btn.setEnabled(true); wall_btn.setEnabled(true); path_btn.setEnabled(true); reset_btn.setEnabled(true); shortest_path_enable_btn.setEnabled(true); is_btn_bg_color_enabled = true; }
                }
            });

            if(Path.size() > 0) { maze_frame_error_bar.setText("DONE!"); timer.start(); }
            else {maze_frame_error_bar.setText("COULDN'T FIND ANY PATH"); run_btn.setEnabled(true); stop_btn.setEnabled(true); wall_btn.setEnabled(true); path_btn.setEnabled(true); reset_btn.setEnabled(true); shortest_path_enable_btn.setEnabled(true); is_btn_bg_color_enabled = true; }
        }
    }

    private boolean search_flag;
    private ArrayList<Dimension> ShortestPath;
    private ArrayList<Dimension> Path;
    private void generate_shortest_path(Dimension D)
    {
        if(Path.contains(D)) { return; }
        if(ShortestPath.size() == 0 || Path.size() < ShortestPath.size()) {Path.add(D);} else { return; }
        
        if(is_path_successful()) 
        {
            if((ShortestPath.size() == 0 || ShortestPath.size() > Path.size())) { ShortestPath = new ArrayList<Dimension>(Path); } 
            if(ShortestPath.size() == shortestDistance(stop_btn_location.get(0),stop_btn_location.get(stop_btn_location.size()-1))) {search_flag = false;}
            Path.remove(D); return; 
        }

        /*
        if(D.width == stop_btn_location.get(stop_btn_location.size()-1).width && D.height == stop_btn_location.get(stop_btn_location.size()-1).height) 
        {
            if((ShortestPath.size() == 0 || ShortestPath.size() > Path.size()) && (is_path_successful())) { ShortestPath = new ArrayList<Dimension>(Path); } 
            if(ShortestPath.size() == shortestDistance(stop_btn_location.get(0),stop_btn_location.get(stop_btn_location.size()-1))) {search_flag = false;}
            Path.remove(D);
            return; 
        }
        */

        Dimension[] temp_dim = getAdjacentBtn(D);
        for(int i = 0; i < temp_dim.length && search_flag; ++i) { if(temp_dim[i] != null) { generate_shortest_path(temp_dim[i]); } }
        if(search_flag) { Path.remove(D);}
    }

    private void generate_path(Dimension D)
    {
        if(Path.contains(D)) { return; }
        Path.add(D);
        if(is_path_successful()) { search_flag = false; return; }

        Dimension[] temp_dim = getAdjacentBtn(D);
        for(int i = 0; i < temp_dim.length && search_flag; ++i) { if(temp_dim[i] != null) { generate_path(temp_dim[i]); } }
        if(search_flag) { Path.remove(D);}
    }

    private boolean is_path_successful()
    {
        int temp_count = 0;
        for(Dimension D : Path) { if(maze_grid_btn[D.height][D.width].getBackground() == stop_color) {++temp_count;} }
        return temp_count == stop_btn_location.size();
    }

    private int shortestDistance(final Dimension D1, final Dimension D2) { return ((D1.width>D2.width)?(D1.width-D2.width):(D2.width-D1.width)) + ((D1.height>D2.height)?(D1.height-D2.height):(D2.height-D1.height)) + 1; }
    private boolean idealPath()
    {
        Path.add(stop_btn_location.get(0));
        while(Path.get(Path.size()-1).width != stop_btn_location.get(stop_btn_location.size()-1).width)
        {
            if(Path.get(Path.size()-1).width < stop_btn_location.get(stop_btn_location.size()-1).width) { Path.add(new Dimension(Path.get(Path.size()-1).width+1,Path.get(Path.size()-1).height)); }
            else { Path.add(new Dimension(Path.get(Path.size()-1).width-1,Path.get(Path.size()-1).height)); }
        }
        while(Path.get(Path.size()-1).height != stop_btn_location.get(stop_btn_location.size()-1).height)
        {
            if(Path.get(Path.size()-1).height < stop_btn_location.get(stop_btn_location.size()-1).height) { Path.add(new Dimension(Path.get(Path.size()-1).width,Path.get(Path.size()-1).height+1)); }
            else { Path.add(new Dimension(Path.get(Path.size()-1).width,Path.get(Path.size()-1).height-1)); }
        }
        if(TestPath()) { ShortestPath = new ArrayList<Dimension>(Path); return true;}

        Path.clear(); Path.add(stop_btn_location.get(0));
        while(Path.get(Path.size()-1).height != stop_btn_location.get(stop_btn_location.size()-1).height)
        {
            if(Path.get(Path.size()-1).height < stop_btn_location.get(stop_btn_location.size()-1).height) { Path.add(new Dimension(Path.get(Path.size()-1).width,Path.get(Path.size()-1).height+1)); }
            else { Path.add(new Dimension(Path.get(Path.size()-1).width,Path.get(Path.size()-1).height-1)); }
        }
        while(Path.get(Path.size()-1).width != stop_btn_location.get(stop_btn_location.size()-1).width)
        {
            if(Path.get(Path.size()-1).width < stop_btn_location.get(stop_btn_location.size()-1).width) { Path.add(new Dimension(Path.get(Path.size()-1).width+1,Path.get(Path.size()-1).height)); }
            else { Path.add(new Dimension(Path.get(Path.size()-1).width-1,Path.get(Path.size()-1).height)); }
        }
        if(TestPath()) { ShortestPath = new ArrayList<Dimension>(Path); return true; }

        Path.clear();
        return false;
    }

    private boolean TestPath()
    {
        boolean flag = true; int temp_count = 0;
        for(Dimension D : Path) { if(maze_grid_btn[D.height][D.width].getBackground() == wall_color) {flag = false; break;} if(maze_grid_btn[D.height][D.width].getBackground() == stop_color) {++temp_count;} }

        return flag && temp_count == stop_btn_location.size() &&
               Path.get(0).width == stop_btn_location.get(0).width && Path.get(0).height == stop_btn_location.get(0).height && 
               Path.get(Path.size()-1).width == stop_btn_location.get(stop_btn_location.size()-1).width && Path.get(Path.size()-1).height == stop_btn_location.get(stop_btn_location.size()-1).height;
    }

    private int first, second, third, forth;
    private Dimension[] getAdjacentBtn(final Dimension center_dim)
    {
        Dimension[] temp_dim_arr = new Dimension[4];

        temp_dim_arr[first] = (center_dim.height>0 && maze_grid_btn[center_dim.height-1][center_dim.width].getBackground() != wall_color) ? (new Dimension(center_dim.width,center_dim.height-1)) : (null); //Top
        temp_dim_arr[second] = (center_dim.width>0 && maze_grid_btn[center_dim.height][center_dim.width-1].getBackground() != wall_color) ? (new Dimension(center_dim.width-1,center_dim.height)) : (null); //Left
        temp_dim_arr[third] = (center_dim.height<maze_grid_btn.length-1 && maze_grid_btn[center_dim.height+1][center_dim.width].getBackground() != wall_color) ? (new Dimension(center_dim.width,center_dim.height+1)) : (null); //Bottom
        temp_dim_arr[forth] = (center_dim.width<maze_grid_btn[0].length-1 && maze_grid_btn[center_dim.height][center_dim.width+1].getBackground() != wall_color) ? (new Dimension(center_dim.width+1,center_dim.height)) : (null); //Right

        return temp_dim_arr;
    }
}


