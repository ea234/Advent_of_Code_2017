package de.ea234.aoc2017.day19;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * <pre>
 * --- Day 19: A Series of Tubes ---
 * https://adventofcode.com/2017/day/19
 * 
 * https://www.reddit.com/r/adventofcode/comments/7kr2ac/2017_day_19_solutions/
 * 
 * 
 * 
 *     0       |          
 *     1       |  +--+    
 *     2       A  |  C    
 *     3   F---|----E|--+ 
 *     4       |  |  |  D 
 *     5       +B-+  +--+ 
 * 
 * 
 * 
 *     0       |          
 *     1       #  ####    
 *     2       #  #  #    
 *     3   ############## 
 *     4       #  #  #  # 
 *     5       ####  #### 
 * 
 * 
 * 
 * Result Part 1 ABCDEF
 * Result Part 2 38
 * 
 * 
 * Result Part 1 HATBMQJYZ
 * Result Part 2 16332
 * 
 * </pre>
 */
public class Day19_ASeriesOfTubes
{
  private static final int  DIRECTION_NORTH      =   1;

  private static final int  DIRECTION_SOUTH      =   2;

  private static final int  DIRECTION_WEST       =   3;

  private static final int  DIRECTION_EAST       =   4;

  private static final int  NO_MOVE_POSSIBLE     = -99;

  private static final char CHAR_PIPE_CROSSING   = '+';

  private static final char CHAR_EMPTY_MAP       = ' ';

  private static final char CHAR_PIPE_VERTICAL   = '|';

  private static final char CHAR_PIPE_HORIZONTAL = '-';

  private static final char CHAR_PIPE_VISITED    = '#';

  public static void main( String[] args )
  {
    String test_input = "     |          ,     |  +--+    ,     A  |  C    , F---|----E|--+ ,     |  |  |  D ,     +B-+  +--+ ";

    calculatePart01( test_input, true );

    calculate01( getListProd(), false );

    System.exit( 0 );
  }

  private static void calculatePart01( String pString, boolean pKnzDebug )
  {
    List< String > converted_string_list = Arrays.stream( pString.split( "," ) ).collect( Collectors.toList() );

    calculate01( converted_string_list, pKnzDebug );
  }

  private static void calculate01( List< String > pListInput, boolean pKnzDebug )
  {
    String result_part_01 = "";

    int result_part_02 = 0;

    /*
     * *******************************************************************************************************
     * Initializing Variables
     * *******************************************************************************************************
     */

    Properties prop_grid_map = new Properties();

    int current_r = 0;

    int robot_row = 0;
    int robot_col = 0;

    int grid_height = pListInput.size();
    int grid_width = pListInput.get( 0 ).length();

    /*
     * *******************************************************************************************************
     * Initializing the grid and determine starting column
     * *******************************************************************************************************
     */

    for ( String input_str : pListInput )
    {
      for ( int current_c = 0; current_c < input_str.length(); current_c++ )
      {
        char current_char = input_str.charAt( (int) current_c );

        if ( current_char != CHAR_EMPTY_MAP )
        {
          prop_grid_map.setProperty( "R" + current_r + "C" + current_c, "" + current_char );

          if ( ( current_r == 0 ) && ( current_char == CHAR_PIPE_VERTICAL ) )
          {
            robot_col = current_c;
          }
        }
      }

      current_r++;
    }

    if ( pKnzDebug )
    {
      wl( "" );
      wl( "" );
      wl( getDebugMap( prop_grid_map, grid_height, grid_width ) );
    }

    /*
     * *******************************************************************************************************
     * Traversing the pipe
     * *******************************************************************************************************
     */

    int cur_direction = DIRECTION_SOUTH;

    boolean can_move = true;

    while ( can_move )
    {
      /*
       * Initialize the temp-variables with their counterpart variables.
       */
      int robot_row_temp = robot_row;
      int robot_col_temp = robot_col;

      /*
       * Determine the new row or col according to the move direction
       */
      if ( cur_direction == DIRECTION_SOUTH )
      {
        robot_row_temp = moveVertical( prop_grid_map, robot_row, robot_col, 1 );
      }
      else if ( cur_direction == DIRECTION_NORTH )
      {
        robot_row_temp = moveVertical( prop_grid_map, robot_row, robot_col, -1 );
      }
      else if ( cur_direction == DIRECTION_EAST )
      {
        robot_col_temp = moveHorizontal( prop_grid_map, robot_row, robot_col, 1 );
      }
      else if ( cur_direction == DIRECTION_WEST )
      {
        robot_col_temp = moveHorizontal( prop_grid_map, robot_row, robot_col, -1 );
      }

      if ( ( robot_row_temp == NO_MOVE_POSSIBLE ) || ( robot_col_temp == NO_MOVE_POSSIBLE ) )
      {
        /*
         * If the move is not possible, the robot cant move. 
         * The variable "can_move" is set to false;
         */
        can_move = false;
      }
      else
      {
        /*
         * If the move is possible, the steps are calculated and 
         * the robot row and col values are updated.
         */
        result_part_02 += Math.abs( robot_row_temp - robot_row );
        result_part_02 += Math.abs( robot_col_temp - robot_col );

        robot_row = robot_row_temp;
        robot_col = robot_col_temp;
      }

      if ( can_move )
      {
        char map_char = getMapChar( prop_grid_map, robot_row, robot_col );

        prop_grid_map.setProperty( "R" + robot_row + "C" + robot_col, "" + CHAR_PIPE_VISITED );

        if ( ( map_char >= 'A' ) && ( map_char <= 'Z' ) )
        {
          result_part_01 += map_char;
        }
        else if ( map_char == CHAR_PIPE_CROSSING )
        {
          if ( ( cur_direction == DIRECTION_WEST ) || ( cur_direction == DIRECTION_EAST ) )
          {
            char map_char_north = getMapChar( prop_grid_map, robot_row - 1, robot_col );
            char map_char_south = getMapChar( prop_grid_map, robot_row + 1, robot_col );

            if ( ( map_char_north == CHAR_PIPE_VERTICAL ) || ( map_char_north >= 'A' ) && ( map_char_north <= 'Z' ) )
            {
              cur_direction = DIRECTION_NORTH;
            }
            else if ( ( map_char_south == CHAR_PIPE_VERTICAL ) || ( map_char_south >= 'A' ) && ( map_char_south <= 'Z' ) )
            {
              cur_direction = DIRECTION_SOUTH;
            }
            else
            {
              can_move = false;
            }
          }
          else
          {
            char map_char_east = getMapChar( prop_grid_map, robot_row, robot_col + 1 );
            char map_char_west = getMapChar( prop_grid_map, robot_row, robot_col - 1 );

            if ( ( map_char_east == CHAR_PIPE_HORIZONTAL ) || ( map_char_east >= 'A' ) && ( map_char_east <= 'Z' ) )
            {
              cur_direction = DIRECTION_EAST;
            }
            else if ( ( map_char_west == CHAR_PIPE_HORIZONTAL ) || ( map_char_west >= 'A' ) && ( map_char_west <= 'Z' ) )
            {
              cur_direction = DIRECTION_WEST;
            }
            else
            {
              can_move = false;
            }
          }
        }
      }
    }

    result_part_02++;

    if ( pKnzDebug )
    {
      wl( "" );
      wl( "" );
      wl( getDebugMap( prop_grid_map, grid_height, grid_width ) );
    }

    wl( "" );
    wl( "" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
    wl( "" );
  }

  private static int moveHorizontal( Properties pMap, int pRow, int pCol, int pDeltaCol )
  {
    /*
     * Next map column to check is "pDeltaCol" away
     */
    int robot_col_temp = pCol + pDeltaCol;

    /*
     * Get the char at the coordinates
     */
    char map_char = getMapChar( pMap, pRow, robot_col_temp );

    /*
     * Skip all characters for a vertical pipe and coordinates that have been visited.
     */
    while ( ( map_char == CHAR_PIPE_VERTICAL ) || ( map_char == CHAR_PIPE_VISITED ) )
    {
      robot_col_temp += pDeltaCol;

      map_char = getMapChar( pMap, pRow, robot_col_temp );
    }

    /*
     * The move is possible if the final char is a horizontal pipe, a crossing, or a letter.
     * The new robot col is returned.
     */
    if ( ( map_char == CHAR_PIPE_HORIZONTAL ) || ( map_char == CHAR_PIPE_CROSSING ) || ( ( map_char >= 'A' ) && ( map_char <= 'Z' ) ) )
    {
      return robot_col_temp;
    }

    /*
     * If the move is not possible, the value of NO_MOVE_POSSIBLE is returned.
     */
    return NO_MOVE_POSSIBLE;
  }

  private static int moveVertical( Properties pMap, int pRow, int pCol, int pDeltaRow )
  {
    /*
     * Next map row to check is "pDeltaRow" away
     */
    int robot_row_temp = pRow + pDeltaRow;

    /*
     * Get the char at the coordinates
     */
    char map_char = getMapChar( pMap, robot_row_temp, pCol );

    /*
     * Skip all characters for a horizontal pipe and coordinates that have been visited.
     */
    while ( ( map_char == CHAR_PIPE_HORIZONTAL ) || ( map_char == CHAR_PIPE_VISITED ) )
    {
      robot_row_temp += pDeltaRow;

      map_char = getMapChar( pMap, robot_row_temp, pCol );
    }

    /*
     * The move is possible if the final char is a vertical pipe, a crossing, or a letter.
     * The new robot row is returned.
     */
    if ( ( map_char == CHAR_PIPE_VERTICAL ) || ( map_char == CHAR_PIPE_CROSSING ) || ( ( map_char >= 'A' ) && ( map_char <= 'Z' ) ) )
    {
      return robot_row_temp;
    }

    /*
     * If the move is not possible, the value of NO_MOVE_POSSIBLE is returned.
     */
    return NO_MOVE_POSSIBLE;
  }

  private static char getMapChar( Properties pMap, int pRow, int pCol )
  {
    return pMap.getProperty( "R" + pRow + "C" + pCol, " " ).charAt( 0 );
  }

  private static String getDebugMap( Properties pMap, int pNumberOfRows, int pNumberOfCols )
  {
    String debug_string = "";

    for ( int cur_row = 0; cur_row < pNumberOfRows; cur_row++ )
    {
      debug_string += String.format( "%5d  ", cur_row );

      for ( int cur_col = 0; cur_col < pNumberOfCols; cur_col++ )
      {
        debug_string += pMap.getProperty( "R" + cur_row + "C" + cur_col, " " );
      }

      debug_string += "\n";
    }

    return debug_string;
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2017__day19_input.txt";

    try
    {
      string_array = Files.readAllLines( Path.of( datei_input ) );
    }
    catch ( IOException e )
    {
      e.printStackTrace();
    }

    return string_array;
  }

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }
}
