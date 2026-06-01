package de.ea234.aoc2017.day10;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * <pre>
 * 
 * --- Day 10: Knot Hash ---
 * https://adventofcode.com/2016/day/10
 * 
 * https://www.reddit.com/r/adventofcode/comments/7irzg5/2017_day_10_solutions/
 * 
 * 
 *  cur_pos    0   cur_len    3   skip    0   start    0   end    2    array [2, 1, 0, 3, 4]
 *  cur_pos    3   cur_len    4   skip    1   start    3   end    1    array [4, 3, 0, 1, 2]
 *  cur_pos    3   cur_len    1   skip    2   start    3   end    3    array [4, 3, 0, 1, 2]
 *  cur_pos    1   cur_len    5   skip    3   start    1   end    0    array [3, 4, 2, 1, 0]
 * 
 *  cur_pos    4   cur_len    0   skip    4   array 
 * 
 * pCircularList.length 5
 * pCircularList[ 0 ]   3
 * pCircularList[ 1 ]   4
 * 
 * Result Part 1 = 12
 * 
 * 
 *  cur_pos    0   cur_len  120   skip    0   start    0   end  119    array [119, 118, 117, 116, 115, 114, 113, 112, 111, 110, 109, 108, 
 *  cur_pos  120   cur_len   93   skip    1   start  120   end  212    array [119, 118, 117, 116, 115, 114, 113, 112, 111, 110, 109, 108, 
 *  cur_pos  214   cur_len    0   skip    2   start  214   end  213    array [119, 118, 117, 116, 115, 114, 113, 112, 111, 110, 109, 108, 
 *  cur_pos  216   cur_len   90   skip    3   start  216   end   49    array [110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 255, 254, 
 *  cur_pos   53   cur_len    5   skip    4   start   53   end   57    array [110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 255, 254, 
 *  cur_pos   62   cur_len   80   skip    5   start   62   end  141    array [110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 255, 254, 
 *  cur_pos  147   cur_len  129   skip    6   start  147   end   19    array [166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 
 *  cur_pos   26   cur_len   74   skip    7   start   26   end   99    array [166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 
 *  cur_pos  107   cur_len    1   skip    8   start  107   end  107    array [166, 167, 168, 169, 170, 171, 172, 173, 174, 175, 176, 177, 
 *  cur_pos  116   cur_len  165   skip    9   start  116   end   24    array [56, 55, 54, 53, 52, 51, 50, 49, 48, 47, 46, 45, 44, 43, 42, 
 *  cur_pos   34   cur_len  204   skip   10   start   34   end  237    array [56, 55, 54, 53, 52, 51, 50, 49, 48, 47, 46, 45, 44, 43, 42, 
 *  cur_pos  248   cur_len  255   skip   11   start  248   end  246    array [118, 7, 6, 5, 4, 3, 2, 1, 0, 212, 211, 210, 209, 208, 207, 2
 *  cur_pos    2   cur_len  254   skip   12   start    2   end  255    array [118, 7, 119, 255, 254, 253, 252, 251, 250, 249, 248, 247, 24
 *  cur_pos   12   cur_len    2   skip   13   start   12   end   13    array [118, 7, 119, 255, 254, 253, 252, 251, 250, 249, 248, 247, 18
 *  cur_pos   27   cur_len   50   skip   14   start   27   end   76    array [118, 7, 119, 255, 254, 253, 252, 251, 250, 249, 248, 247, 18
 *  cur_pos   91   cur_len  113   skip   15   start   91   end  203    array [118, 7, 119, 255, 254, 253, 252, 251, 250, 249, 248, 247, 18
 * 
 *  cur_pos  219   cur_len    0   skip   16   array 
 * 
 * pCircularList.length 256
 * pCircularList[ 0 ]   118
 * pCircularList[ 1 ]   7
 * 
 * Result Part 1 = 826
 * 
 * 
 * </pre> 
 */
public class Day10_KnotHash
{
  public static void main( String[] args )
  {
    int[] test_circular_list = { 0, 1, 2, 3, 4 };

    int[] test_lengths       = { 3, 4, 1, 5 };

    calcList( test_circular_list, test_lengths );

    /*
     * 
     */

    String lengths = "120,93,0,90,5,80,129,74,1,165,204,255,254,2,50,113";

    int[] input_lengths = Arrays.stream( lengths.split( "," ) ).map( String::trim ).mapToInt( Integer::parseInt ).toArray();

    int[] circular_list = IntStream.rangeClosed( 0, 255 ).toArray();

    calcList( circular_list, input_lengths );

    System.exit( 0 );
  }

  private static void calcList( int[] pCircularList, int[] pListInputLengths )
  {
    int cur_position = 0;

    int skip_size    = 0;

    /*
     * Loop over all input lengths
     */
    for ( int cur_length : pListInputLengths )
    {
      int index_start = cur_position;

      int index_end   = ( ( cur_position + cur_length ) - 1 ) % pCircularList.length;

      int index_start_dbg = index_start;

      int index_end_dbg   = index_end;

      if ( cur_length > 0 )
      {
        while ( index_start != index_end )
        {
          /*
           * Swap int values between index-start and index-end
           */
          int temp = pCircularList[ index_end ];

          pCircularList[ index_end ] = pCircularList[ index_start ];

          pCircularList[ index_start ] = temp;

          /*
           * Increase index-start by 1
           */
          index_start++;

          /*
           * If index-start is equal to the circular-list length, index-start is set to 0.
           */
          if ( index_start == pCircularList.length )
          {
            index_start = 0;
          }

          /*
           * After incrementing index-start by one, there has to be a check, wecher 
           * index-start has reached the index-end.
           */
          if ( index_start != index_end )
          {
            /*
             * Decrease index-end by 1
             */
            index_end--;

            /*
             * If index-end is -1, then index-end is set to the last index
             */
            if ( index_end == -1 )
            {
              index_end = pCircularList.length - 1;
            }
          }
        }
      }

      wl( String.format( " cur_pos %4d   cur_len %4d   skip %4d   start %4d   end %4d    array ", cur_position, cur_length, skip_size, index_start_dbg, index_end_dbg ) + Arrays.toString( pCircularList ) );

      /*
       * Calculate the new current position
       */
      cur_position = ( ( ( cur_position + cur_length ) ) + skip_size ) % pCircularList.length;

      /*
       * Increase the skip-size by one
       */
      skip_size++;
    }

    wl( "" );
    wl( String.format( " cur_pos %4d   cur_len %4d   skip %4d   array ", cur_position, 0, skip_size ) );
    wl( "" );
    wl( "pCircularList.length " + pCircularList.length );
    wl( "pCircularList[ 0 ]   " + pCircularList[ 0 ] );
    wl( "pCircularList[ 1 ]   " + pCircularList[ 1 ] );

    int result_part_01 = pCircularList[ 0 ] * pCircularList[ 1 ];

    wl( "" );
    wl( "Result Part 1 = " + result_part_01 );
    wl( "" );
  }

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }
}

