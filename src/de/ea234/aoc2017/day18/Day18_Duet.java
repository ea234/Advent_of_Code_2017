package de.ea234.aoc2017.day18;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * 
 * --- Day 18: Duet ---
 * https://adventofcode.com/2017/day/18
 * 
 * https://www.reddit.com/r/adventofcode/comments/7kj35s/2017_day_18_solutions/
 * 
 * 
 * ------------------------------------------------------------------------------------------
 * RUN 
 * 
 *      0    0 SET    "a"  VALUE   OLD           0   TO           1   NEW           1
 *      1    1 ADD    "a"  VALUE   OLD           1   +            2   NEW           3
 *      2    2 MUL    "a"  VALUE   OLD           3   *            3   NEW           9
 *      3    3 MOD    "a"  VALUE   OLD           9   MO           5   NEW           4
 *      4    4 SND    "a"  VALUE                 4 
 *      5    5 SET    "a"  VALUE   OLD           4   TO           0   NEW           0
 *      6    6 RCV    "a"  VALUE                 0 =           4
 *      7    7 JGZ 0  "a"  VALUE                 0
 *      8    8 SET    "a"  VALUE   OLD           0   TO           1   NEW           1
 *      9    9 JGZ 1  "a"  VALUE                 1
 *     10    7 JGZ 1  "a"  VALUE                 1
 *     11    6 RCV    "a"  VALUE                 1 =           4   ###################
 * 
 * Register 97  a  = 1
 * Register 98  b  = 0
 * Register 99  c  = 0
 * Register 100  d  = 0
 * Register 101  e  = 0
 * Register 102  f  = 0
 * Register 103  g  = 0
 * Register 104  h  = 0
 * Register 105  i  = 0
 * Register 106  j  = 0
 * Register 107  k  = 0
 * Register 108  l  = 0
 * Register 109  m  = 0
 * Register 110  n  = 0
 * Register 111  o  = 0
 * Register 112  p  = 0
 * Register 113  q  = 0
 * Register 114  r  = 0
 * Register 115  s  = 0
 * Register 116  t  = 0
 * Register 117  u  = 0
 * Register 118  v  = 0
 * Register 119  w  = 0
 * Register 120  x  = 0
 * Register 121  y  = 0
 * Register 122  z  = 0
 * 
 * ------------------------------------------------------------------------------------------
 * Result Part 1 4
 * Result Part 2 0
 * 
 *   1257   17 SND    "b"  VALUE              9329 
 *   1267   17 SND    "b"  VALUE              1260 
 *   1277   17 SND    "b"  VALUE              6323 
 *   1287   17 SND    "b"  VALUE              2970 
 *   1297   17 SND    "b"  VALUE                31 
 *   1307   17 SND    "b"  VALUE              2468 
 *   1317   17 SND    "b"  VALUE              9552 
 *   1327   17 SND    "b"  VALUE              4886 
 *   1337   17 SND    "b"  VALUE                43 
 *   1347   17 SND    "b"  VALUE              7679 
 *   1357   17 SND    "b"  VALUE              8888 
 *   1367   17 SND    "b"  VALUE              2951 
 *   1373   25 RCV    "a"  VALUE        2147483647 =        2951   ###################
 * 
 * Register 97  a  = 2147483647
 * Register 98  b  = 2951
 * Register 99  c  = 0
 * Register 100  d  = 0
 * Register 101  e  = 0
 * Register 102  f  = 0
 * Register 103  g  = 0
 * Register 104  h  = 0
 * Register 105  i  = 126
 * Register 106  j  = 0
 * Register 107  k  = 0
 * Register 108  l  = 0
 * Register 109  m  = 0
 * Register 110  n  = 0
 * Register 111  o  = 0
 * Register 112  p  = 1842102951
 * Register 113  q  = 0
 * Register 114  r  = 0
 * Register 115  s  = 0
 * Register 116  t  = 0
 * Register 117  u  = 0
 * Register 118  v  = 0
 * Register 119  w  = 0
 * Register 120  x  = 0
 * Register 121  y  = 0
 * Register 122  z  = 0
 * 
 * ------------------------------------------------------------------------------------------
 * Result Part 1 2951
 * Result Part 2 0
 * 
 * </pre> 
 */
public class Day18_Duet
{
  public static void main( String[] args )
  {
    String test_input = "set a 1,add a 2,mul a a,mod a 5,snd a,set a 0,rcv a,jgz a -1,set a 1,jgz a -2";

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
    long result_part_01 = 0;
    long result_part_02 = 0;

    result_part_01 = run( pListInput, pKnzDebug );
    //result_part_02 = run( pListInput, pKnzDebug );

    wl( "" );
    wl( "------------------------------------------------------------------------------------------" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
  }

  private static long run( List< String > pListInput, boolean pKnzDebug )
  {
    if ( pKnzDebug )
    {
      wl( "" );
      wl( "------------------------------------------------------------------------------------------" );
      wl( "RUN " );
      wl( "" );
    }

    int index_recover_sound = 0;
    int index_op_old_value = 1;

    long[] register_vector = new long[ 130 ];

    for ( int idx = 97; idx < 128; idx++ )
    {
      register_vector[ idx ] = 0;
    }

    int pgm_counter = 0;

    int step_counter = 0;

    while ( ( pgm_counter < pListInput.size() ) && ( step_counter < 1_000_000_000 ) )
    {
      String input_str = pListInput.get( pgm_counter );

      char char_register_op = input_str.charAt( 4 );

      if ( input_str.startsWith( "snd" ) )
      {
        register_vector[ index_recover_sound ] = register_vector[ ( (int) char_register_op ) ];

        wl( String.format( "%6d %4d SND    \"" + char_register_op + "\"  VALUE       %11d ", step_counter, pgm_counter, register_vector[ ( (int) char_register_op ) ] ) );

        pgm_counter++;
      }
      else if ( input_str.startsWith( "rcv" ) )
      {
        if ( register_vector[ ( (int) char_register_op ) ] > 0 )
        {
          wl( String.format( "%6d %4d RCV    \"" + char_register_op + "\"  VALUE       %11d = %11d   ###################", step_counter, pgm_counter, register_vector[ ( (int) char_register_op ) ], register_vector[ index_recover_sound ] ) );

          break;
        }
        else
        {
          wl( String.format( "%6d %4d RCV    \"" + char_register_op + "\"  VALUE       %11d = %11d", step_counter, pgm_counter, register_vector[ ( (int) char_register_op ) ], register_vector[ index_recover_sound ] ) );
        }

        pgm_counter++;
      }
      else
      {
        char char_register_from = input_str.charAt( 6 );

        register_vector[ index_op_old_value ] = register_vector[ ( (int) char_register_op ) ];

        long ins_val = 0;

        if ( ( char_register_from >= 'a' ) && ( char_register_from <= 'z' ) )
        {
          ins_val = register_vector[ ( (int) char_register_from ) ];
        }
        else
        {
          ins_val = Integer.parseInt( input_str.substring( 6 ) );
        }

        if ( input_str.startsWith( "set" ) )
        {
          register_vector[ ( (int) char_register_op ) ] = ins_val;

          if ( pKnzDebug )
          {
            wl( String.format( "%6d %4d SET    \"" + char_register_op + "\"  VALUE   OLD %11d   TO %11d   NEW %11d", step_counter, pgm_counter, register_vector[ index_op_old_value ], ins_val, register_vector[ ( (int) char_register_op ) ] ) );
          }

          pgm_counter++;
        }
        else if ( input_str.startsWith( "add" ) )
        {
          register_vector[ ( (int) char_register_op ) ] += ins_val;

          if ( pKnzDebug )
          {
            wl( String.format( "%6d %4d ADD    \"" + char_register_op + "\"  VALUE   OLD %11d   +  %11d   NEW %11d", step_counter, pgm_counter, register_vector[ index_op_old_value ], ins_val, register_vector[ ( (int) char_register_op ) ] ) );
          }

          pgm_counter++;
        }
        else if ( input_str.startsWith( "mul" ) )
        {
          register_vector[ ( (int) char_register_op ) ] *= ins_val;

          if ( pKnzDebug )
          {
            wl( String.format( "%6d %4d MUL    \"" + char_register_op + "\"  VALUE   OLD %11d   *  %11d   NEW %11d", step_counter, pgm_counter, register_vector[ index_op_old_value ], ins_val, register_vector[ ( (int) char_register_op ) ] ) );
          }

          pgm_counter++;
        }
        else if ( input_str.startsWith( "mod" ) )
        {
          register_vector[ ( (int) char_register_op ) ] = register_vector[ ( (int) char_register_op ) ] % ins_val;

          if ( pKnzDebug )
          {
            wl( String.format( "%6d %4d MOD    \"" + char_register_op + "\"  VALUE   OLD %11d   MO %11d   NEW %11d", step_counter, pgm_counter, register_vector[ index_op_old_value ], ins_val, register_vector[ ( (int) char_register_op ) ] ) );
          }

          pgm_counter++;
        }
        else if ( input_str.startsWith( "jgz" ) )
        {
          int old_pgm_counter = pgm_counter;

          if ( register_vector[ ( (int) char_register_op ) ] > 0 )
          {
            pgm_counter += ins_val;

            if ( pKnzDebug )
            {
              wl( String.format( "%6d %4d JGZ 1  \"" + char_register_op + "\"  VALUE       %11d", step_counter, old_pgm_counter, register_vector[ ( (int) char_register_op ) ] ) );
            }

          }
          else
          {
            if ( pKnzDebug )
            {
              wl( String.format( "%6d %4d JGZ 0  \"" + char_register_op + "\"  VALUE       %11d", step_counter, old_pgm_counter, register_vector[ ( (int) char_register_op ) ] ) );
            }

            pgm_counter++;
          }
        }
      }

      step_counter++;
    }

    wl( "" );

    for ( int idx = 97; idx < 123; idx++ )
    {
      wl( "Register " + idx + "  " + ( (char) idx ) + "  = " + register_vector[ idx ] );
    }

    return register_vector[ index_recover_sound ];
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2017__day18_input.txt";

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
