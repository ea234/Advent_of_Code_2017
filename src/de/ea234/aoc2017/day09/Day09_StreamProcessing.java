package de.ea234.aoc2017.day09;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * <pre>
 * --- Day 9: Stream Processing ---
 * https://adventofcode.com/2017/day/9
 * 
 * https://www.reddit.com/r/adventofcode/comments/7iksqc/2017_day_9_solutions/
 * 
 * Input {}                               group_count   1  group_score_val      1 
 * Input {{{}}}                           group_count   3  group_score_val      6 
 * Input {{},{}}                          group_count   3  group_score_val      5 
 * Input {{{},{},{{}}}}                   group_count   6  group_score_val     16 
 * Input {<{},{},{{}}>}                   group_count   1  group_score_val      1 
 * Input {<a>,<a>,<a>,<a>}                group_count   1  group_score_val      1 
 * Input {{<a>},{<a>},{<a>},{<a>}}        group_count   5  group_score_val      9 
 * Input {{<!>},{<!>},{<!>},{<a>}}        group_count   2  group_score_val      3 
 * Input {{<ab>},{<ab>},{<ab>},{<ab>}}    group_count   5  group_score_val      9 
 * Input {{<!!>},{<!!>},{<!!>},{<!!>}}    group_count   5  group_score_val      9 
 * Input {{<a!>},{<a!>},{<a!>},{<ab>}}    group_count   2  group_score_val      3 
 * 
 * Result Part 1 1290
 * Result Part 2 0
 * 
 * {{<ab>},{<ab>},{<ab>},{<ab>}}
 * cur_index     0   cur_char {   group_count    0   group_sorce_cur    0   knz_in_garbage    0   score      0 
 * cur_index     1   cur_char {   group_count    0   group_sorce_cur    1   knz_in_garbage    0   score      0 
 * cur_index     2   cur_char <   group_count    0   group_sorce_cur    2   knz_in_garbage    0   score      0 
 * cur_index     3   cur_char a   group_count    0   group_sorce_cur    2   knz_in_garbage    1   score      0 
 * cur_index     4   cur_char b   group_count    0   group_sorce_cur    2   knz_in_garbage    1   score      0 
 * cur_index     5   cur_char >   group_count    0   group_sorce_cur    2   knz_in_garbage    1   score      0 
 * cur_index     6   cur_char }   group_count    0   group_sorce_cur    2   knz_in_garbage    0   score      0 
 * cur_index     7   cur_char ,   group_count    1   group_sorce_cur    1   knz_in_garbage    0   score      2 
 * cur_index     8   cur_char {   group_count    1   group_sorce_cur    1   knz_in_garbage    0   score      2 
 * cur_index     9   cur_char <   group_count    1   group_sorce_cur    2   knz_in_garbage    0   score      2 
 * cur_index    10   cur_char a   group_count    1   group_sorce_cur    2   knz_in_garbage    1   score      2 
 * cur_index    11   cur_char b   group_count    1   group_sorce_cur    2   knz_in_garbage    1   score      2 
 * cur_index    12   cur_char >   group_count    1   group_sorce_cur    2   knz_in_garbage    1   score      2 
 * cur_index    13   cur_char }   group_count    1   group_sorce_cur    2   knz_in_garbage    0   score      2 
 * cur_index    14   cur_char ,   group_count    2   group_sorce_cur    1   knz_in_garbage    0   score      4 
 * cur_index    15   cur_char {   group_count    2   group_sorce_cur    1   knz_in_garbage    0   score      4 
 * cur_index    16   cur_char <   group_count    2   group_sorce_cur    2   knz_in_garbage    0   score      4 
 * cur_index    17   cur_char a   group_count    2   group_sorce_cur    2   knz_in_garbage    1   score      4 
 * cur_index    18   cur_char b   group_count    2   group_sorce_cur    2   knz_in_garbage    1   score      4 
 * cur_index    19   cur_char >   group_count    2   group_sorce_cur    2   knz_in_garbage    1   score      4 
 * cur_index    20   cur_char }   group_count    2   group_sorce_cur    2   knz_in_garbage    0   score      4 
 * cur_index    21   cur_char ,   group_count    3   group_sorce_cur    1   knz_in_garbage    0   score      6 
 * cur_index    22   cur_char {   group_count    3   group_sorce_cur    1   knz_in_garbage    0   score      6 
 * cur_index    23   cur_char <   group_count    3   group_sorce_cur    2   knz_in_garbage    0   score      6 
 * cur_index    24   cur_char a   group_count    3   group_sorce_cur    2   knz_in_garbage    1   score      6 
 * cur_index    25   cur_char b   group_count    3   group_sorce_cur    2   knz_in_garbage    1   score      6 
 * cur_index    26   cur_char >   group_count    3   group_sorce_cur    2   knz_in_garbage    1   score      6 
 * cur_index    27   cur_char }   group_count    3   group_sorce_cur    2   knz_in_garbage    0   score      6 
 * cur_index    28   cur_char }   group_count    4   group_sorce_cur    1   knz_in_garbage    0   score      8 
 *
 * 
 * {{<a!>},{<a!>},{<a!>},{<ab>}}
 * cur_index     0   cur_char {   group_count    0   group_sorce_cur    0   knz_in_garbage    0   score      0 
 * cur_index     1   cur_char {   group_count    0   group_sorce_cur    1   knz_in_garbage    0   score      0 
 * cur_index     2   cur_char <   group_count    0   group_sorce_cur    2   knz_in_garbage    0   score      0 
 * cur_index     3   cur_char a   group_count    0   group_sorce_cur    2   knz_in_garbage    1   score      0 
 * cur_index     4   cur_char !   group_count    0   group_sorce_cur    2   knz_in_garbage    1   score      0 -- Ignore next character 
 * cur_index     6   cur_char }   group_count    0   group_sorce_cur    2   knz_in_garbage    1   score      0 
 * cur_index     7   cur_char ,   group_count    0   group_sorce_cur    2   knz_in_garbage    1   score      0 
 * cur_index     8   cur_char {   group_count    0   group_sorce_cur    2   knz_in_garbage    1   score      0 
 * cur_index     9   cur_char <   group_count    0   group_sorce_cur    2   knz_in_garbage    1   score      0 
 * cur_index    10   cur_char a   group_count    0   group_sorce_cur    2   knz_in_garbage    1   score      0 
 * cur_index    11   cur_char !   group_count    0   group_sorce_cur    2   knz_in_garbage    1   score      0 -- Ignore next character 
 * cur_index    13   cur_char }   group_count    0   group_sorce_cur    2   knz_in_garbage    1   score      0 
 * cur_index    14   cur_char ,   group_count    0   group_sorce_cur    2   knz_in_garbage    1   score      0 
 * cur_index    15   cur_char {   group_count    0   group_sorce_cur    2   knz_in_garbage    1   score      0 
 * cur_index    16   cur_char <   group_count    0   group_sorce_cur    2   knz_in_garbage    1   score      0 
 * cur_index    17   cur_char a   group_count    0   group_sorce_cur    2   knz_in_garbage    1   score      0 
 * cur_index    18   cur_char !   group_count    0   group_sorce_cur    2   knz_in_garbage    1   score      0 -- Ignore next character
 * cur_index    20   cur_char }   group_count    0   group_sorce_cur    2   knz_in_garbage    1   score      0 
 * cur_index    21   cur_char ,   group_count    0   group_sorce_cur    2   knz_in_garbage    1   score      0 
 * cur_index    22   cur_char {   group_count    0   group_sorce_cur    2   knz_in_garbage    1   score      0 
 * cur_index    23   cur_char <   group_count    0   group_sorce_cur    2   knz_in_garbage    1   score      0 
 * cur_index    24   cur_char a   group_count    0   group_sorce_cur    2   knz_in_garbage    1   score      0 
 * cur_index    25   cur_char b   group_count    0   group_sorce_cur    2   knz_in_garbage    1   score      0 
 * cur_index    26   cur_char >   group_count    0   group_sorce_cur    2   knz_in_garbage    1   score      0 
 * cur_index    27   cur_char }   group_count    0   group_sorce_cur    2   knz_in_garbage    0   score      0 
 * cur_index    28   cur_char }   group_count    1   group_sorce_cur    1   knz_in_garbage    0   score      2 
 * 
 * 
 * </pre>
 */
public class Day09_StreamProcessing
{
  public static void main( String[] args )
  {
    parseStream( "{}",                            true );
    parseStream( "{{{}}}",                        true );
    parseStream( "{{},{}}",                       true );
    parseStream( "{{{},{},{{}}}}",                true );
    parseStream( "{<{},{},{{}}>}",                true );
    parseStream( "{<a>,<a>,<a>,<a>}",             true );
    parseStream( "{{<a>},{<a>},{<a>},{<a>}}",     true );
    parseStream( "{{<!>},{<!>},{<!>},{<a>}}",     true );
    parseStream( "{{<ab>},{<ab>},{<ab>},{<ab>}}", true );
    parseStream( "{{<!!>},{<!!>},{<!!>},{<!!>}}", true );
    parseStream( "{{<a!>},{<a!>},{<a!>},{<ab>}}", true );

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
        result_part_01 += parseStream( input_str, pKnzDebug );
      }
    }

    wl( "" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
    wl( "" );
  }

  private static long parseStream( String pInput, boolean pKnzDebug )
  {
    long knz_in_garbage = 0;

    long group_score_cur = 0;

    long group_score_val = 0;

    long group_count = 0;

    for ( int cur_index = 0; cur_index < pInput.length(); cur_index++ )
    {
      char cur_char = pInput.charAt( cur_index );

      if ( pKnzDebug )
      {
        //wl( String.format( "cur_index %5d   cur_char %s   group_count %4d   group_sorce_cur %4d   knz_in_garbage %4d   score %6d ", cur_index, cur_char, group_count, group_score_cur, knz_in_garbage, group_score_val ) );
      }

      if ( knz_in_garbage > 0 )
      {
        if ( cur_char == '!' )
        {
          /*
           * If the cur_char is '!', the next character is ignored.
           * Just increment the current parser index.
           */
          cur_index++;
        }
        else if ( cur_char == '>' )
        {
          /*
           * If the cur_char is '>', the level of the garbage brackets 
           * is decremented by one
           */
          knz_in_garbage--;
        }
      }
      else
      {
        if ( cur_char == '<' )
        {
          knz_in_garbage++;
        }
        else if ( cur_char == '{' )
        {
          group_score_cur++;
        }
        else if ( cur_char == '}' )
        {
          group_score_val += group_score_cur;

          group_score_cur--;

          group_count++;
        }
      }
    }

    if ( pKnzDebug )
    {
      wl( String.format( "Input %-30s   group_count %3d  group_score_val %6d ", pInput, group_count, group_score_val ) );
    }

    return group_count;
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2017__day09_input.txt";

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
