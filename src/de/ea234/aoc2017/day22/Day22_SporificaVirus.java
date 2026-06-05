package de.ea234.aoc2017.day22;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * <pre>
 * 
 * --- Day 22: Sporifica Virus ---
 * https://adventofcode.com/2017/day/22
 * 
 * https://www.reddit.com/r/adventofcode/comments/7lf943/2017_day_22_solutions/
 * 
 * 
 *   -10  .............................................
 *    -9  .............................................
 *    -8  .............................................
 *    -7  .........##..##..##..........................
 *    -6  ........#########..#.........................
 *    -5  .......#.##..##...###........................
 *    -4  .......#..#..#..#####........................
 *    -3  ........#.#.##.#...#.........................
 *    -2  ........#..#....#..#.........................
 *    -1  .......###...##...###........................
 *     0  .........#......###..#.......................
 *     1  .......#...#...##...###......................
 *     2  .........#.###....###..#.....................
 *     3  .....#..#....##...#...###....................
 *     4  .....###.######..#..###..#...................
 *     5  ......#..#..######..#...###..................
 *     6  .......##....##..#.#..###..#.................
 *     7  ..................##..#...###................
 *     8  ...................#.#..###..#...............
 *     9  ....................##..#...###..............
 *    10  .....................#.#..###..#.............
 *    11  ......................##..#...###............
 *    12  .......................#.#..###..#...........
 *    13  ........................##..#...###..........
 *    14  .........................#.#..###..#.........
 *    15  ..........................##..#...###........
 *    16  ...........................#.#..###..#.......
 *    17  ............................##..#...###......
 *    18  .............................#.#..###..#.....
 *    19  ..............................##..#...###....
 *    20  ...............................#.#..###..#...
 *    21  ................................##..#...###..
 *    22  .................................#.#..###..#.
 *    23  ..................................##..#...###
 *    24  ...................................#.#..###..
 *    25  ....................................##..#...#
 *   
 * bursts_infected 5587
 * bursts_cleaned  4413
 *                 10000
 *
 * ------------------------------------------------------------------------------------------
 * PART 2
 * 
 * bursts_weakened 2537219
 * bursts_flagged  2487680
 * bursts_infected 2511944
 * bursts_cleaned  2463157
 *                 10000000
 * 
 * Result Part 2 2511944
 * 
 * ------------------------------------------------------------------------------------------
 * bursts_weakened 2537266
 * bursts_flagged  2487686
 * bursts_infected 2511978
 * bursts_cleaned  2463070
 *                 10000000
 * 
 * Result Part 2 2511978
 * 
 * </pre> 
 */
public class Day22_SporificaVirus
{
  private static final char FACING_NORTH       = 'N';

  private static final char FACING_SOUTH       = 'S';

  private static final char FACING_EAST        = 'E';

  private static final char FACING_WEST        = 'W';

  private static final char TURN_LEFT          = 'L';

  private static final char TURN_RIGHT         = 'R';

  private static final char CHAR_CELL_INFECTED = '#';

  private static final char CHAR_CELL_CLEAN    = '.';

  private static final char CHAR_CELL_WEAKENED = 'w';

  private static final char CHAR_CELL_FLAGGED  = 'F';

  public static void main( String[] args )
  {
    String test_input = "..#,#..,...";

    //calculatePart01( test_input, 10_000, true );

    //calculate01( getListProd(), 10_000, false );

    calculatePart02( test_input, 10_000_000, true );

    calculate02( getListProd(), 10_000_000, false );

    System.exit( 0 );
  }

  private static void calculatePart01( String pString, int pBursts, boolean pKnzDebug )
  {
    List< String > converted_string_list = Arrays.stream( pString.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    calculate01( converted_string_list, pBursts, pKnzDebug );
  }

  private static void calculate01( List< String > pListInput, int pBursts, boolean pKnzDebug )
  {
    wl( "" );
    wl( "------------------------------------------------------------------------------------------" );

    long result_part_01 = 0;

    /*
     * *******************************************************************************************************
     * Initializing the grid
     * *******************************************************************************************************
     */

    long grid_height = pListInput.size();
    long grid_width  = pListInput.get( 0 ).length();

    long start_row   = grid_height / 2;
    long start_col   = grid_width / 2;

    long current_row = 0;
    long current_col = 0;

    Properties prop_map = new Properties();

    for ( String input_str : pListInput )
    {
      if ( pKnzDebug )
      {
        wl( input_str );
      }

      for ( current_col = 0; current_col < input_str.length(); current_col++ )
      {
        char current_char = input_str.charAt( (int) current_col );

        if ( current_char == CHAR_CELL_INFECTED )
        {
          prop_map.setProperty( "R" + current_row + "C" + current_col, "" + CHAR_CELL_INFECTED );
        }
      }

      current_row++;
    }

    /*
     * *******************************************************************************************************
     * Doing the loops
     * *******************************************************************************************************
     */

    current_row = start_row;
    current_col = start_col;

    char cur_facing      = FACING_NORTH;

    long bursts_infected = 0;
    long bursts_cleaned  = 0;

    long min_rows        = -2;
    long min_cols        = -2;

    long max_rows        = grid_height + 2;
    long max_cols        = grid_width  + 2;

    for ( long step_count = 0; step_count < pBursts; step_count++ )
    {
      String current_node_coords = "R" + current_row + "C" + current_col;

      char current_node_status = prop_map.getProperty( current_node_coords, "" + CHAR_CELL_CLEAN ).charAt( 0 );

      if ( current_node_status == CHAR_CELL_INFECTED )
      {
        /*
         * If the current node is infected, it turns to its right
         */
        cur_facing = getNewFacing90( cur_facing, TURN_RIGHT );

        /*
         * If the current node is infected, it becomes cleaned
         */
        prop_map.setProperty( current_node_coords, "" + CHAR_CELL_CLEAN );

        bursts_cleaned++;
      }
      else
      {
        /*
         * If the current node is clean, it turns to its left
         */
        cur_facing = getNewFacing90( cur_facing, TURN_LEFT );

        /*
         * If the current node is clean, it becomes infected
         */
        prop_map.setProperty( current_node_coords, "" + CHAR_CELL_INFECTED );

        bursts_infected++;
      }

      /*
       * Moving 1 step in the facing direction
       */
      int move_delta_row = 0;

      int move_delta_col = 0;

      if ( cur_facing == FACING_NORTH )
      {
        move_delta_row = -1;
      }
      else if ( cur_facing == FACING_SOUTH )
      {
        move_delta_row = 1;
      }
      else if ( cur_facing == FACING_WEST )
      {
        move_delta_col = -1;
      }
      else if ( cur_facing == FACING_EAST )
      {
        move_delta_col = 1;
      }

      current_row += move_delta_row;

      current_col += move_delta_col;

      /*
       * Updating the grid boundaries for debug purposes
       */
      min_rows = Math.min( min_rows, current_row + ( -5 ) );

      max_rows = Math.max( max_rows, current_row + 5 );

      min_cols = Math.min( min_cols, current_col + ( -5 ) );

      max_cols = Math.max( max_cols, current_col + 5 );
    }

    //wl( "" );
    //wl( getDebugMap( prop_map, min_rows, min_cols, max_rows, max_cols ) );

    result_part_01 = bursts_infected;

    wl( "bursts_infected " + bursts_infected );
    wl( "bursts_cleaned  " + bursts_cleaned );
    wl( "                " + ( bursts_infected + bursts_cleaned ) );
    wl( "" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "" );
  }

  private static void calculatePart02( String pString, int pBursts, boolean pKnzDebug )
  {
    List< String > converted_string_list = Arrays.stream( pString.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    calculate02( converted_string_list, pBursts, pKnzDebug );
  }

  private static void calculate02( List< String > pListInput, int pBursts, boolean pKnzDebug )
  {
    wl( "" );
    wl( "------------------------------------------------------------------------------------------" );

    long result_part_02 = 0;

    /*
     * *******************************************************************************************************
     * Initializing the grid
     * *******************************************************************************************************
     */

    long grid_height = pListInput.size();
    long grid_width  = pListInput.get( 0 ).length();

    long start_row   = grid_height / 2;
    long start_col   = grid_width / 2;

    long current_row = 0;
    long current_col = 0;

    Properties prop_map = new Properties();

    for ( String input_str : pListInput )
    {
      if ( pKnzDebug )
      {
        wl( input_str );
      }

      for ( current_col = 0; current_col < input_str.length(); current_col++ )
      {
        char current_char = input_str.charAt( (int) current_col );

        if ( current_char == CHAR_CELL_INFECTED )
        {
          prop_map.setProperty( "R" + current_row + "C" + current_col, "" + CHAR_CELL_INFECTED );
        }
      }

      current_row++;
    }

    /*
     * *******************************************************************************************************
     * Doing the loops
     * *******************************************************************************************************
     */

    current_row = start_row;
    current_col = start_col;

    char cur_facing      = FACING_NORTH;

    long bursts_infected = 0;
    long bursts_cleaned  = 0;
    long bursts_weakened = 0;
    long bursts_flagged  = 0;

    long min_rows        = -2;
    long min_cols        = -2;

    long max_rows        = grid_height + 2;
    long max_cols        = grid_width  + 2;

    for ( long step_count = 0; step_count < pBursts; step_count++ )
    {
      String current_node_coords = "R" + current_row + "C" + current_col;

      char current_node_status = prop_map.getProperty( current_node_coords, "" + CHAR_CELL_CLEAN ).charAt( 0 );

      /*
       * Decide which way to turn based on the current node
       */
      if ( current_node_status == CHAR_CELL_CLEAN )
      {
        /*
         * If it is clean, it turns left.
         */
        cur_facing = getNewFacing90( cur_facing, TURN_LEFT );
      }
      else if ( current_node_status == CHAR_CELL_INFECTED )
      {
        /*
         * If the current node is infected, it turns to its right
         */
        cur_facing = getNewFacing90( cur_facing, TURN_RIGHT );
      }
      else if ( current_node_status == CHAR_CELL_FLAGGED )
      {
        /*
         * If the current node is flagged, it reverses direction
         */
        cur_facing = getNewFacing90( cur_facing, TURN_RIGHT );
        cur_facing = getNewFacing90( cur_facing, TURN_RIGHT );
      }
      //else
      //{
      //  /*
      //   * If the current node is weakened, it does not turn
      //   */
      //}

      /*
       * Infect the cell
       */
      if ( current_node_status == CHAR_CELL_CLEAN )
      {
        /*
         * If the current node is clean, it becomes weakened
         */
        prop_map.setProperty( current_node_coords, "" + CHAR_CELL_WEAKENED );

        bursts_weakened++;
      }
      else if ( current_node_status == CHAR_CELL_WEAKENED )
      {
        /*
         * If the current node is weakened, it becomes infected
         */
        prop_map.setProperty( current_node_coords, "" + CHAR_CELL_INFECTED );

        bursts_infected++;
      }
      else if ( current_node_status == CHAR_CELL_INFECTED )
      {
        /*
         * If the current node is infected, it becomes flagged
         */
        prop_map.setProperty( current_node_coords, "" + CHAR_CELL_FLAGGED );

        bursts_flagged++;
      }
      else
      {
        /*
         * If the current node is flagged, it becomes cleaned
         */
        prop_map.setProperty( current_node_coords, "" + CHAR_CELL_CLEAN );

        bursts_cleaned++;
      }

      /*
       * Moving 1 step in the facing direction
       */
      int move_delta_row = 0;

      int move_delta_col = 0;

      if ( cur_facing == FACING_NORTH )
      {
        move_delta_row = -1;
      }
      else if ( cur_facing == FACING_SOUTH )
      {
        move_delta_row = 1;
      }
      else if ( cur_facing == FACING_WEST )
      {
        move_delta_col = -1;
      }
      else if ( cur_facing == FACING_EAST )
      {
        move_delta_col = 1;
      }

      current_row += move_delta_row;

      current_col += move_delta_col;

      /*
       * Updating the grid boundaries for debug purposes
       */
      min_rows = Math.min( min_rows, current_row + ( -5 ) );

      max_rows = Math.max( max_rows, current_row + 5 );

      min_cols = Math.min( min_cols, current_col + ( -5 ) );

      max_cols = Math.max( max_cols, current_col + 5 );
    }

    //wl( "" );
    //wl( getDebugMap( prop_map, min_rows, min_cols, max_rows, max_cols ) );

    result_part_02 = bursts_infected;

    wl( "bursts_weakened " + bursts_weakened );
    wl( "bursts_flagged  " + bursts_flagged );
    wl( "bursts_infected " + bursts_infected );
    wl( "bursts_cleaned  " + bursts_cleaned );
    wl( "                " + ( bursts_infected + bursts_cleaned + bursts_weakened + bursts_flagged ) );
    wl( "" );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
    wl( "" );
  }

  private static char getNewFacing90( char pCurFacing, char pTurnDirection )
  {
    if ( pTurnDirection == TURN_LEFT )
    {
      if ( pCurFacing == FACING_NORTH ) return FACING_WEST;
      if ( pCurFacing == FACING_WEST ) return FACING_SOUTH;
      if ( pCurFacing == FACING_SOUTH ) return FACING_EAST;
      //if ( pCurFacing === FACING_EAST  ) return FACING_NORTH;
    }
    else if ( pTurnDirection == TURN_RIGHT )
    {
      if ( pCurFacing == FACING_NORTH ) return FACING_EAST;
      if ( pCurFacing == FACING_EAST ) return FACING_SOUTH;
      if ( pCurFacing == FACING_SOUTH ) return FACING_WEST;
      //if ( pCurFacing === FACING_WEST  ) return FACING_NORTH;
    }

    return FACING_NORTH;
  }

  private static char getMapChar( Properties pMap, int pRow, int pCol )
  {
    return pMap.getProperty( "R" + pRow + "C" + pCol, " " ).charAt( 0 );
  }

  private static String getDebugMap( Properties pMap, long pMinRows, long pMinCols, long pMaxRows, long pMaxCols )
  {
    String debug_string = "";

    for ( long cur_row = pMinRows; cur_row < pMaxRows; cur_row++ )
    {
      debug_string += String.format( "%5d  ", cur_row );

      for ( long cur_col = pMinCols; cur_col < pMaxCols; cur_col++ )
      {
        debug_string += pMap.getProperty( "R" + cur_row + "C" + cur_col, "" + CHAR_CELL_CLEAN );
      }

      debug_string += "\n";
    }

    return debug_string;
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2017__day22_input.txt";

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
