package de.ea234.aoc2017.day06;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * <pre>
 * --- Day 6: Memory Reallocation ---
 * https://adventofcode.com/2017/day/6
 * 
 * https://www.reddit.com/r/adventofcode/comments/7hvtoq/2017_day_6_solutions/
 * 
 * 
 * 
 * Part 01 ----------------------------------------------------------------------------------------------------
 * 
 *   1   idx_highest   1   blocks   7  [2, 4, 1, 2]
 *   2   idx_highest   1   blocks   4  [3, 1, 2, 3]
 *   3   idx_highest   3   blocks   3  [0, 2, 3, 4]
 *   4   idx_highest   3   blocks   4  [1, 3, 4, 1]
 *   5   idx_highest   2   blocks   4  [2, 4, 1, 2]
 * 
 * Part 02 ----------------------------------------------------------------------------------------------------
 * 
 *   1   idx_highest   1   blocks   4  [3, 1, 2, 3]
 *   2   idx_highest   3   blocks   3  [0, 2, 3, 4]
 *   3   idx_highest   3   blocks   4  [1, 3, 4, 1]
 *   4   idx_highest   2   blocks   4  [2, 4, 1, 2]
 * 
 * Result Part 1 5
 * Result Part 2 4
 * 
 * 
 * 
 * Result Part 1 7864
 * Result Part 2 1695
 * 
 * </pre>
 */
public class Day06_MemoryReallocation
{
  public static void main( String[] args )
  {
    calculate01( "0  2  7  0 ", true );

    calculate01( "0	5	10	0	11	14	13	4	11	8	8	7	1	4	12	11", false );

    System.exit( 0 );
  }

  private static void calculate01( String pString, boolean pKnzDebug )
  {
    List< String > string_list = Arrays.stream( pString.trim().split( "\\s+" ) ).map( String::trim ).collect( Collectors.toList() );

    long[] memory_banks_vector = string_list.stream().map( String::trim ).mapToLong( Long::parseLong ).toArray();

    int result_part_01 = getStepCount( memory_banks_vector, 1, pKnzDebug );
    int result_part_02 = getStepCount( memory_banks_vector, 2, pKnzDebug );

    wl( "" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
    wl( "" );
  }

  private static int getStepCount( long[] memory_banks_vector, int pPart, boolean pKnzDebug )
  {
    if ( pKnzDebug )
    {
      wl( "" );
      wl( "Part 0" + pPart + " ----------------------------------------------------------------------------------------------------" );
      wl( "" );
    }

    long memory_banks_count = memory_banks_vector.length;

    int step_counter = 0;

    boolean knz_do_loop = true;

    Properties prop_mem_config = new Properties();

    prop_mem_config.setProperty( Arrays.toString( memory_banks_vector ), "" + step_counter );

    while ( knz_do_loop )
    {
      /*
       * Find the memory bank with the most blocks (ties won by the lowest-numbered memory bank) 
       */
      int idx_highest_memory_bank = 0;

      for ( int idx_cur = 0; idx_cur < memory_banks_count; idx_cur++ )
      {
        if ( memory_banks_vector[ idx_cur ] > memory_banks_vector[ idx_highest_memory_bank ] )
        {
          idx_highest_memory_bank = idx_cur;
        }
      }

      long redist_blocks = memory_banks_vector[ idx_highest_memory_bank ];

      long debug_save_value = redist_blocks;

      /*
       * Remove all of the blocks from the selected bank
       */
      memory_banks_vector[ idx_highest_memory_bank ] = 0;

      /*
       * Move to the next memory bank and insert one of the blocks. 
       * Continues doing this until it runs out of blocks.
       */
      while ( redist_blocks > 0 )
      {
        idx_highest_memory_bank++;

        if ( idx_highest_memory_bank >= memory_banks_count )
        {
          idx_highest_memory_bank = 0;
        }

        memory_banks_vector[ idx_highest_memory_bank ]++;

        redist_blocks--;
      }

      step_counter++;

      String memory_bank_config = Arrays.toString( memory_banks_vector );

      if ( pKnzDebug )
      {
        wl( String.format( "%3d   idx_highest %3d   blocks %3d  %s", step_counter, idx_highest_memory_bank, debug_save_value, memory_bank_config ) );
      }

      if ( prop_mem_config.getProperty( memory_bank_config ) == null )
      {
        prop_mem_config.setProperty( memory_bank_config, "" + step_counter );
      }
      else
      {
        knz_do_loop = false;
      }
    }

    return step_counter;
  }

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }
}