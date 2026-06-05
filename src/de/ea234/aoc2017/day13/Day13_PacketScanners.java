package de.ea234.aoc2017.day13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * 
 * --- Day 13: Packet Scanners ---
 * https://adventofcode.com/2017/day/13
 * 
 * https://www.reddit.com/r/adventofcode/comments/7jgyrt/2017_day_13_solutions/
 * 
 * ------------------------------------------------------------------------------------------
 * 
 * Depth   0   Range   3   Sum if cought     0
 * Depth   1   Range   2   Sum if cought     2
 * Depth   4   Range   4   Sum if cought    16
 * Depth   6   Range   4   Sum if cought    24
 * 
 * Cought on depth   0 with range   3 (=    0)
 * Cought on depth   6 with range   4 (=   24)
 * 
 * Result Part 1 24
 * Result Part 2 0
 * 
 * ------------------------------------------------------------------------------------------
 * 
 * Depth   0   Range   4   Sum if cought     0
 * Depth   1   Range   2   Sum if cought     2
 * Depth   2   Range   3   Sum if cought     6
 * Depth   4   Range   4   Sum if cought    16
 * Depth   6   Range   8   Sum if cought    48
 * Depth   8   Range   5   Sum if cought    40
 * Depth  10   Range   8   Sum if cought    80
 * Depth  12   Range   6   Sum if cought    72
 * Depth  14   Range   6   Sum if cought    84
 * Depth  16   Range   8   Sum if cought   128
 * Depth  18   Range   6   Sum if cought   108
 * Depth  20   Range   6   Sum if cought   120
 * Depth  22   Range  12   Sum if cought   264
 * Depth  24   Range  12   Sum if cought   288
 * Depth  26   Range  10   Sum if cought   260
 * Depth  28   Range   8   Sum if cought   224
 * Depth  30   Range  12   Sum if cought   360
 * Depth  32   Range   8   Sum if cought   256
 * Depth  34   Range  12   Sum if cought   408
 * Depth  36   Range   9   Sum if cought   324
 * Depth  38   Range  12   Sum if cought   456
 * Depth  40   Range   8   Sum if cought   320
 * Depth  42   Range  12   Sum if cought   504
 * Depth  44   Range  17   Sum if cought   748
 * Depth  46   Range  14   Sum if cought   644
 * Depth  48   Range  12   Sum if cought   576
 * Depth  50   Range  10   Sum if cought   500
 * Depth  52   Range  20   Sum if cought  1040
 * Depth  54   Range  12   Sum if cought   648
 * Depth  56   Range  14   Sum if cought   784
 * Depth  58   Range  14   Sum if cought   812
 * Depth  60   Range  14   Sum if cought   840
 * Depth  62   Range  12   Sum if cought   744
 * Depth  64   Range  14   Sum if cought   896
 * Depth  66   Range  14   Sum if cought   924
 * Depth  68   Range  14   Sum if cought   952
 * Depth  70   Range  14   Sum if cought   980
 * Depth  72   Range  12   Sum if cought   864
 * Depth  74   Range  14   Sum if cought  1036
 * Depth  76   Range  14   Sum if cought  1064
 * Depth  80   Range  14   Sum if cought  1120
 * Depth  84   Range  18   Sum if cought  1512
 * Depth  88   Range  14   Sum if cought  1232
 * 
 * Cought on depth   0 with range   4 (=    0)
 * Cought on depth   8 with range   5 (=   40)
 * Cought on depth  20 with range   6 (=  120)
 * Cought on depth  22 with range  12 (=  264)
 * Cought on depth  28 with range   8 (=  224)
 * 
 * ------------------------------------------------------------------------------------------
 * Result Part 1 648
 * Result Part 2 0
 * 
 * 
 * </pre> 
 */
public class Day13_PacketScanners
{
  public static void main( String[] args )
  {
    String test_input = "0: 3,1: 2,4: 4,6: 4";

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
    wl( "" );
    wl( "------------------------------------------------------------------------------------------" );

    int result_part_01 = 0;
    int result_part_02 = 0;

    /*
     * *******************************************************************************************************
     * Determine the max depth from the input
     * *******************************************************************************************************
     */

    int max_depth = 0;

    for ( String input_str : pListInput )
    {
      if ( !input_str.isBlank() )
      {
        int cur_depth = Integer.parseInt( input_str.substring( 0, input_str.indexOf( ":" ) ) );

        max_depth = Math.max( max_depth, cur_depth );
      }
    }

    max_depth++;

    /*
     * *******************************************************************************************************
     * Parsing the input and creating the depth arrays
     * *******************************************************************************************************
     */

    int[] depth_array = new int[ max_depth ];
    int[] depth_range = new int[ max_depth ];
    int[] depth_delta = new int[ max_depth ];

    for ( String input_str : pListInput )
    {
      if ( !input_str.isBlank() )
      {
        int index_delimiter = input_str.indexOf( ":" );

        int cur_depth = Integer.parseInt( input_str.substring( 0, index_delimiter ) );
        int cur_range = Integer.parseInt( input_str.substring( index_delimiter + 1 ).trim() );

        depth_array[ cur_depth ] = cur_range;
        depth_range[ cur_depth ] = 1;
        depth_delta[ cur_depth ] = -1;

        wl( String.format( "Depth %3d   Range %3d   Sum if cought %5d", cur_depth, depth_array[ cur_depth ], ( cur_depth * depth_array[ cur_depth ] ) ) );
      }
    }

    /*
     * *******************************************************************************************************
     * Simulating the pass of a packet through the scanners
     * *******************************************************************************************************
     */

    wl( "" );

    result_part_01 = calcRisk( depth_array, depth_range, depth_delta, max_depth, 0 );

    result_part_02 = 0;

    for ( int delay_time = 1; delay_time < 15_000; delay_time++ )
    {
      int result_temp = calcRisk( depth_array, depth_range, depth_delta, max_depth, delay_time );

      if ( result_temp == 0 )
      {
        wl( String.format( "Delay %3d   Risk %6d", delay_time, result_temp ) );

        result_part_02 = delay_time;

        break;
      }
    }

    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
  }

  private static int calcRisk( int[] pDepth, int[] pRange, int[] pDelta, int pMaxDepth, int pDelayTime )
  {
    int result_value = 0;

    /*
     * Resetting the arrays to start position
     */
    for ( int idx_1 = 0; idx_1 < pMaxDepth; idx_1++ )
    {
      if ( pDepth[ idx_1 ] != 0 )
      {
        pRange[ idx_1 ] = 1;
        pDelta[ idx_1 ] = -1;
      }
    }

    /*
     * Advancing the scanners through the delay time.
     */
    for ( int idx_delay = 0; idx_delay < pDelayTime; idx_delay++ )
    {
      for ( int idx_1 = 0; idx_1 < pMaxDepth; idx_1++ )
      {
        if ( ( pRange[ idx_1 ] == pDepth[ idx_1 ] ) || ( pRange[ idx_1 ] == 1 ) )
        {
          pDelta[ idx_1 ] *= -1;
        }

        pRange[ idx_1 ] += pDelta[ idx_1 ];
      }
    }

    /*
     * Simulating the packet traversing through the scanners
     */
    for ( int idx_packet = 0; idx_packet < pMaxDepth; idx_packet++ )
    {
      if ( pRange[ idx_packet ] == 1 )
      {
        result_value += ( idx_packet * pDepth[ idx_packet ] );

        //wl( String.format( "Cought on depth %3d with range %3d (=%5d)", idx_packet, pDepth[ idx_packet ], ( idx_packet * pDepth[ idx_packet ] ) ) );
      }

      for ( int idx_1 = idx_packet; idx_1 < pMaxDepth; idx_1++ )
      {
        if ( ( pRange[ idx_1 ] == pDepth[ idx_1 ] ) || ( pRange[ idx_1 ] == 1 ) )
        {
          pDelta[ idx_1 ] *= -1;
        }

        pRange[ idx_1 ] += pDelta[ idx_1 ];
      }
    }

    return result_value;
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2017__day13_input.txt";

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
