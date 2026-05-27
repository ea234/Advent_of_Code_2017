package de.ea234.aoc2017.day02;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * --- Day 1: Inverse Captcha ---
 * https://adventofcode.com/2017/day/2
 * 
 * https://www.reddit.com/r/adventofcode/comments/7h0rnm/2017_day_2_solutions/
 * 
 * 
 * ValueA      5   ValueB      9   Modulo     5      4  Result     0
 * ValueA      5   ValueB      2   Modulo     1      2  Result     2
 * ValueA      5   ValueB      8   Modulo     5      3  Result     0
 * ValueA      9   ValueB      2   Modulo     1      2  Result     4
 * ValueA      9   ValueB      8   Modulo     1      8  Result     1
 * ValueA      2   ValueB      8   Modulo     2      0  Result     0
 * ### B ###   ValueA      2   ValueB      8   Result     4
 * Input  5 9 2 8     CheckSum    4
 * 
 * ValueA      9   ValueB      4   Modulo     1      4  Result     2
 * ValueA      9   ValueB      7   Modulo     2      7  Result     1
 * ValueA      9   ValueB      3   Modulo     0      3  Result     3
 * ### A ###   ValueA      9   ValueB      3   Result     3
 * Input  9 4 7 3     CheckSum    3
 * 
 * ValueA      3   ValueB      8   Modulo     3      2  Result     0
 * ValueA      3   ValueB      6   Modulo     3      0  Result     0
 * ### B ###   ValueA      3   ValueB      6   Result     2
 * Input  3 8 6 5     CheckSum    2
 * 
 * Result Part 1 18
 * Result Part 2 7
 * 
 * ----------------------------------------------------------------------- 
 * 
 * Result Part 1 46402
 * Result Part 2 265
 * 
 * </pre>
 */
public class Day02_CorruptionChecksum
{

  public static void main( String[] args )
  {
    testCalcCheckSum02( " 5 9 2 8 " );
    testCalcCheckSum02( " 9 4 7 3 " );
    testCalcCheckSum02( " 3 8 6 5 " );

    String test_input = "5 1 9 5,7 5 3,2 4 6 8";

    calculatePart01( test_input, true );

    calculate01( getListProd(), true );

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

    for ( String input_str : pListInput )
    {
      if ( !input_str.isBlank() )
      {
        result_part_01 += calcCheckSum01( input_str );
        result_part_02 += calcCheckSum02( input_str, false );
      }
    }

    wl( "" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
    wl( "" );
  }

  private static void testCalcCheckSum02( String pString )
  {
    wl( "" );

    int check_sum = calcCheckSum02( pString, true );

    wl( String.format( "Input %-10s   CheckSum %4d", pString, check_sum ) );
  }

  private static int calcCheckSum01( String pInput )
  {
    List< Integer > intList = Arrays.stream( pInput.trim().split( "\\s+" ) ).map( Integer::valueOf ).collect( Collectors.toList() );

    int min_value = Integer.MAX_VALUE;
    int max_value = -999_099_999;

    for ( int cur_value : intList )
    {
      min_value = Math.min( min_value, cur_value );
      max_value = Math.max( max_value, cur_value );
    }

    return max_value - min_value;
  }

  private static int calcCheckSum02( String pInput, boolean pKnzDebug )
  {
    List< Integer > intList = Arrays.stream( pInput.trim().split( "\\s+" ) ).map( Integer::valueOf ).collect( Collectors.toList() );

    int min_value = Integer.MAX_VALUE;
    int max_value = -999_099_999;

    int list_len = intList.size();

    int idx1 = 0;
    int idx2 = 0;

    for ( idx1 = 0; idx1 < list_len; idx1++ )
    {
      int idx1_value = intList.get( idx1 ).intValue();

      for ( idx2 = idx1 + 1; idx2 < list_len; idx2++ )
      {
        int idx2_value = intList.get( idx2 ).intValue();

        if ( pKnzDebug )
        {
          wl( String.format( "ValueA  %5d   ValueB  %5d   Modulo %5d  %5d  Result %5d", idx1_value, idx2_value, ( idx1_value % idx2_value ), ( idx2_value % idx1_value ), ( idx1_value / idx2_value ) ) );
        }

        if ( ( idx1_value % idx2_value ) == 0 )
        {
          if ( pKnzDebug )
          {
            wl( String.format( "### A ###   ValueA  %5d   ValueB  %5d   Result %5d", idx1_value, idx2_value, ( idx1_value / idx2_value ) ) );
          }

          return idx1_value / idx2_value;
        }
        else if ( ( idx2_value % idx1_value ) == 0 )
        {
          if ( pKnzDebug )
          {
            wl( String.format( "### B ###   ValueA  %5d   ValueB  %5d   Result %5d", idx1_value, idx2_value, ( idx2_value / idx1_value ) ) );
          }

          return idx2_value / idx1_value;
        }
      }
    }

    return 0;
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2017__day02_input.txt";

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