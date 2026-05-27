package de.ea234.aoc2017.day01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * <pre>
 * --- Day 1: Inverse Captcha ---
 * https://adventofcode.com/2017/day/1
 * 
 * https://www.reddit.com/r/adventofcode/comments/7gsrc2/2017_day_1_solutions/
 * 
 * Input 1122         CheckSum    3
 * Input 1111         CheckSum    4
 * Input 1234         CheckSum    0
 * Input 91212129     CheckSum    9
 * 
 * len   4   delta   2   idx   0   idxc   2   CharA 1   CharB 1   CheckSum    1 
 * len   4   delta   2   idx   1   idxc   3   CharA 2   CharB 2   CheckSum    3 
 * len   4   delta   2   idx   2   idxc   0   CharA 1   CharB 1   CheckSum    4 
 * len   4   delta   2   idx   3   idxc   1   CharA 2   CharB 2   CheckSum    6 
 * Input 1212         CheckSum    6
 * 
 * len   4   delta   2   idx   0   idxc   2   CharA 1   CharB 2   CheckSum    0 
 * len   4   delta   2   idx   1   idxc   3   CharA 2   CharB 1   CheckSum    0 
 * len   4   delta   2   idx   2   idxc   0   CharA 2   CharB 1   CheckSum    0 
 * len   4   delta   2   idx   3   idxc   1   CharA 1   CharB 2   CheckSum    0 
 * Input 1221         CheckSum    0
 * 
 * len   6   delta   3   idx   0   idxc   3   CharA 1   CharB 4   CheckSum    0 
 * len   6   delta   3   idx   1   idxc   4   CharA 2   CharB 2   CheckSum    2 
 * len   6   delta   3   idx   2   idxc   5   CharA 3   CharB 5   CheckSum    2 
 * len   6   delta   3   idx   3   idxc   0   CharA 4   CharB 1   CheckSum    2 
 * len   6   delta   3   idx   4   idxc   1   CharA 2   CharB 2   CheckSum    4 
 * len   6   delta   3   idx   5   idxc   2   CharA 5   CharB 3   CheckSum    4 
 * Input 123425       CheckSum    4
 * 
 * len   6   delta   3   idx   0   idxc   3   CharA 1   CharB 1   CheckSum    1 
 * len   6   delta   3   idx   1   idxc   4   CharA 2   CharB 2   CheckSum    3 
 * len   6   delta   3   idx   2   idxc   5   CharA 3   CharB 3   CheckSum    6 
 * len   6   delta   3   idx   3   idxc   0   CharA 1   CharB 1   CheckSum    7 
 * len   6   delta   3   idx   4   idxc   1   CharA 2   CharB 2   CheckSum    9 
 * len   6   delta   3   idx   5   idxc   2   CharA 3   CharB 3   CheckSum   12 
 * Input 123123       CheckSum   12
 * 
 * len   8   delta   4   idx   0   idxc   4   CharA 1   CharB 1   CheckSum    1 
 * len   8   delta   4   idx   1   idxc   5   CharA 2   CharB 4   CheckSum    1 
 * len   8   delta   4   idx   2   idxc   6   CharA 1   CharB 1   CheckSum    2 
 * len   8   delta   4   idx   3   idxc   7   CharA 3   CharB 5   CheckSum    2 
 * len   8   delta   4   idx   4   idxc   0   CharA 1   CharB 1   CheckSum    3 
 * len   8   delta   4   idx   5   idxc   1   CharA 4   CharB 2   CheckSum    3 
 * len   8   delta   4   idx   6   idxc   2   CharA 1   CharB 1   CheckSum    4 
 * len   8   delta   4   idx   7   idxc   3   CharA 5   CharB 3   CheckSum    4 
 * Input 12131415     CheckSum    4
 * 
 * Result Part 1 1089
 * Result Part 2 1156
 * 
 * </pre>
 */
public class Day01_InverseCaptcha
{

  public static void main( String[] args )
  {
    testInverseCaptcha01( "1122"     );
    testInverseCaptcha01( "1111"     );
    testInverseCaptcha01( "1234"     );
    testInverseCaptcha01( "91212129" );

    testInverseCaptcha02( "1212"     );
    testInverseCaptcha02( "1221"     );
    testInverseCaptcha02( "123425"   );
    testInverseCaptcha02( "123123"   );
    testInverseCaptcha02( "12131415" );

    calculate01( getListProd(), true );

    System.exit( 0 );
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

  private static void testInverseCaptcha01( String pString )
  {
    int check_sum = calcCheckSum01( pString );

    wl( String.format( "Input %-10s   CheckSum %4d", pString, check_sum ) );
  }

  private static void testInverseCaptcha02( String pString )
  {
    wl( "" );

    int check_sum = calcCheckSum02( pString, true );

    wl( String.format( "Input %-10s   CheckSum %4d", pString, check_sum ) );
  }

  private static int calcCheckSum01( String pInput )
  {
    int check_sum = 0;

    char last_char = pInput.charAt( 0 );

    for ( int idx = 1; idx < pInput.length(); idx++ )
    {
      if ( last_char == pInput.charAt( idx ) )
      {
        check_sum += ( (int) last_char ) - 48;
      }

      last_char = pInput.charAt( idx );
    }

    if ( last_char == pInput.charAt( 0 ) )
    {
      check_sum += ( (int) last_char ) - 48;
    }

    return check_sum;
  }

  private static int calcCheckSum02( String pInput, boolean pKnzDebug )
  {
    int len_input = pInput.length();

    int delta_x = len_input / 2;

    int check_sum = 0;

    for ( int idx = 0; idx < len_input; idx++ )
    {
      int idx_compare = ( idx + delta_x ) % len_input;

      char cur_char_idx = pInput.charAt( idx );

      if ( cur_char_idx == pInput.charAt( idx_compare ) )
      {
        check_sum += ( (int) cur_char_idx ) - 48;
      }

      if ( pKnzDebug )
      {
        wl( String.format( "len %3d   delta %3d   idx %3d   idxc %3d   CharA %s   CharB %s   CheckSum %4d ", len_input, delta_x, idx, idx_compare, cur_char_idx, pInput.charAt( idx_compare ), check_sum ) );
      }
    }

    return check_sum;
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2017__day01_input.txt";

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