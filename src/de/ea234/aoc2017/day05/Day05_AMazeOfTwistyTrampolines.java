package de.ea234.aoc2017.day05;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * --- Day 5: A Maze of Twisty Trampolines, All Alike --- ---
 * https://adventofcode.com/2017/day/5
 * 
 * https://www.reddit.com/r/adventofcode/comments/7hngbn/2017_day_5_solutions/
 * 
 * 
 * Part 01 ----------------------------------------------------------------------------------------------------
 * 
 *   1   cur_idx   0   last_idx   0   last_val   0   [1, 3, 0, 1, -3]
 *   2   cur_idx   1   last_idx   0   last_val   1   [2, 3, 0, 1, -3]
 *   3   cur_idx   4   last_idx   1   last_val   3   [2, 4, 0, 1, -3]
 *   4   cur_idx   1   last_idx   4   last_val  -3   [2, 4, 0, 1, -2]
 *   5   cur_idx   5   last_idx   1   last_val   4   [2, 5, 0, 1, -2]
 * 
 * Part 02 ----------------------------------------------------------------------------------------------------
 * 
 *   1   cur_idx   0   last_idx   0   last_val   0   [1, 3, 0, 1, -3]
 *   2   cur_idx   1   last_idx   0   last_val   1   [2, 3, 0, 1, -3]
 *   3   cur_idx   4   last_idx   1   last_val   3   [2, 2, 0, 1, -3]
 *   4   cur_idx   1   last_idx   4   last_val  -3   [2, 2, 0, 1, -2]
 *   5   cur_idx   3   last_idx   1   last_val   2   [2, 3, 0, 1, -2]
 *   6   cur_idx   4   last_idx   3   last_val   1   [2, 3, 0, 2, -2]
 *   7   cur_idx   2   last_idx   4   last_val  -2   [2, 3, 0, 2, -1]
 *   8   cur_idx   2   last_idx   2   last_val   0   [2, 3, 1, 2, -1]
 *   9   cur_idx   3   last_idx   2   last_val   1   [2, 3, 2, 2, -1]
 *  10   cur_idx   5   last_idx   3   last_val   2   [2, 3, 2, 3, -1]
 * 
 * Result Part 1 5
 * Result Part 2 10
 * 
 * 
 * 
 * Result Part 1 374269
 * Result Part 2 27720699
 * 
 * </pre>
 */
public class Day05_AMazeOfTwistyTrampolines
{
  public static void main( String[] args )
  {
    String test_input = "0,3,0,1,-3";

    calculatePart01( test_input, true );

    calculate01( getListProd(), false );

    System.exit( 0 );
  }

  private static void calculatePart01( String pString, boolean pKnzDebug )
  {
    List< String > converted_string_list = Arrays.stream( pString.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    calculate01( converted_string_list, pKnzDebug );
  }

  private static void calculate01( List< String > pListInput, boolean pKnzDebug )
  {
    int result_part_01 = 0;

    int result_part_02 = 0;

    {
      if ( pKnzDebug )
      {
        wl( "" );
        wl( "Part 01 ----------------------------------------------------------------------------------------------------" );
        wl( "" );
      }
      
      long[] instruction_array = pListInput.stream().mapToLong( Long::parseLong ).toArray();

      int step_counter  = 0;

      int cur_idx       = 0;

      int last_idx      = 0;

      long last_idx_val = 0;

      while ( cur_idx < instruction_array.length )
      {
        last_idx = cur_idx;

        cur_idx += instruction_array[ cur_idx ];

        step_counter++;

        last_idx_val = instruction_array[ last_idx ];

        instruction_array[ last_idx ]++;

        if ( pKnzDebug )
        {
          wl( String.format( "%3d   cur_idx %3d   last_idx %3d   last_val %3d   %s", step_counter, cur_idx, last_idx, last_idx_val, Arrays.toString( instruction_array ) ) );
        }
      }

      result_part_01 = step_counter;
    }

    {
      if ( pKnzDebug )
      {
        wl( "" );
        wl( "Part 02 ----------------------------------------------------------------------------------------------------" );
        wl( "" );
      }

      long[] instruction_array = pListInput.stream().mapToLong( Long::parseLong ).toArray();

      int step_counter  = 0;

      int cur_idx       = 0;

      int last_idx      = 0;

      long last_idx_val = 0;

      while ( cur_idx < instruction_array.length )
      {
        last_idx = cur_idx;

        cur_idx += instruction_array[ cur_idx ];

        step_counter++;

        last_idx_val = instruction_array[ last_idx ];

        if ( instruction_array[ last_idx ] >= 3 )
        {
          instruction_array[ last_idx ]--;
        }
        else
        {
          instruction_array[ last_idx ]++;
        }

        if ( pKnzDebug )
        {
          wl( String.format( "%3d   cur_idx %3d   last_idx %3d   last_val %3d   %s", step_counter, cur_idx, last_idx, last_idx_val, Arrays.toString( instruction_array ) ) );
        }
      }

      result_part_02 = step_counter;
    }

    wl( "" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
    wl( "" );
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2017__day05_input.txt";

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
