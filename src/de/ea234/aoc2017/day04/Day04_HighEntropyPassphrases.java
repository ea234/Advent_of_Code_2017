package de.ea234.aoc2017.day04;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * <pre>
 * --- Day 4: High-Entropy Passphrases ---
 * https://adventofcode.com/2017/day/4
 * 
 * https://www.reddit.com/r/adventofcode/comments/7hf5xb/2017_day_4_solutions/
 * 
 * 
 * </pre>
 */
public class Day04_HighEntropyPassphrases
{
  public static void main( String[] args )
  {
    testTestPassphrase02( "aa bb cc dd ee" );
    testTestPassphrase02( "aa bb cc dd aa" );

    checkAnagram( "Test", "Ttse", true );
    checkAnagram( "iioi", "iiio", true );

    calculate01( getListProd(), false );

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
        result_part_01 += checkPassphrase01( input_str );

        result_part_02 += checkPassphrase02( input_str, pKnzDebug );
      }
    }

    wl( "" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
    wl( "" );
  }

  private static void testTestPassphrase02( String pString )
  {
    wl( "" );

    int check_sum = checkPassphrase01( pString );

    wl( String.format( "Input %-10s  %s", pString, ( check_sum == 1 ? "valid" : "" ) ) );
  }

  private static int checkPassphrase01( String pInput )
  {
    List< String > converted_string_list = Arrays.stream( pInput.split( " " ) ).map( String::trim ).collect( Collectors.toList() );

    Properties prop_w = new Properties();

    for ( String cur_string : converted_string_list )
    {
      if ( prop_w.get( cur_string ) == null )
      {
        prop_w.setProperty( cur_string, cur_string );
      }
      else
      {
        return 0;
      }
    }

    return 1;
  }

  private static int checkPassphrase02( String pInput, boolean pKnzDebug )
  {
    List< String > converted_string_list = Arrays.stream( pInput.split( " " ) ).map( String::trim ).collect( Collectors.toList() );

    Properties prop_w = new Properties();

    for ( String cur_string : converted_string_list )
    {
      if ( prop_w.get( cur_string ) == null )
      {
        prop_w.setProperty( cur_string, cur_string );
      }
      else
      {
        return 0;
      }
    }

    int list_len = converted_string_list.size();

    int idx1 = 0;
    int idx2 = 0;

    for ( idx1 = 0; idx1 < list_len; idx1++ )
    {
      String idx1_value = converted_string_list.get( idx1 );

      for ( idx2 = idx1 + 1; idx2 < list_len; idx2++ )
      {
        String idx2_value = converted_string_list.get( idx2 );

        if ( checkAnagram( idx1_value, idx2_value, pKnzDebug ) )
        {
          return 0;
        }
      }
    }

    return 1;
  }

  private static boolean checkAnagram( String pInputA, String pInputB, boolean pKnzDebug )
  {
    /*
     * Different Length = no Anagram
     */
    if ( pInputA.length() != pInputB.length() )
    {
      return false;
    }

    char[] arr1 = pInputA.toCharArray();
    char[] arr2 = pInputB.toCharArray();

    Arrays.sort( arr1 );
    Arrays.sort( arr2 );

    boolean knz_is_anagram = Arrays.equals( arr1, arr2 );

    if ( ( pKnzDebug ) || knz_is_anagram )
    {
      wl( String.format( "A = \"%-12s\"   B = \"%-12s\" IsAnagramm " + knz_is_anagram, pInputA, pInputB ) );
    }

    return knz_is_anagram;
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2017__day04_input.txt";

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