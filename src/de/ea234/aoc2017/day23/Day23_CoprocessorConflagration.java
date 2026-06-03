package de.ea234.aoc2017.day23;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * 
 * --- Day 23: Coprocessor Conflagration ---
 * https://adventofcode.com/2017/day/23
 * 
 * https://www.reddit.com/r/adventofcode/comments/7lms6p/2017_day_23_solutions/
 * 
 * ------------------------------------------------------------------------------------------
 * RUN 
 * 
 *      0    0 SET    "b"  VALUE   OLD           0   TO          81   NEW          81
 *      1    1 SET    "c"  VALUE   OLD           0   TO          81   NEW          81
 *      2    2 JNZ 0  "a"  VALUE                 0
 *      3    3 JNZ 0  "1"  VALUE                 0
 *      4    4 MUL    "b"  VALUE   OLD          81   *          100   NEW        8100
 *      5    5 SUB    "b"  VALUE   OLD        8100   +      -100000   NEW      108100
 *      6    6 SET    "c"  VALUE   OLD          81   TO      108100   NEW      108100
 *      7    7 SUB    "c"  VALUE   OLD      108100   +       -17000   NEW      125100
 *      8    8 SET    "f"  VALUE   OLD           0   TO           1   NEW           1
 *      9    9 SET    "d"  VALUE   OLD           0   TO           2   NEW           2
 *     10   10 SET    "e"  VALUE   OLD           0   TO           2   NEW           2
 *     11   11 SET    "g"  VALUE   OLD           0   TO           2   NEW           2
 *     12   12 MUL    "g"  VALUE   OLD           2   *            2   NEW           4
 *     13   13 SUB    "g"  VALUE   OLD           4   +       108100   NEW     -108096
 *     14   14 JNZ 1  "g"  VALUE           -108096
 *     15   16 SUB    "e"  VALUE   OLD           2   +           -1   NEW           3
 *     16   17 SET    "g"  VALUE   OLD     -108096   TO           3   NEW           3
 *     17   18 SUB    "g"  VALUE   OLD           3   +       108100   NEW     -108097
 *     18   19 JNZ 1  "g"  VALUE           -108097
 *     19   11 SET    "g"  VALUE   OLD     -108097   TO           2   NEW           2
 *     20   12 MUL    "g"  VALUE   OLD           2   *            3   NEW           6
 *     21   13 SUB    "g"  VALUE   OLD           6   +       108100   NEW     -108094
 *     22   14 JNZ 1  "g"  VALUE           -108094
 *     23   16 SUB    "e"  VALUE   OLD           3   +           -1   NEW           4
 *     24   17 SET    "g"  VALUE   OLD     -108094   TO           4   NEW           4
 *     25   18 SUB    "g"  VALUE   OLD           4   +       108100   NEW     -108096
 *     26   19 JNZ 1  "g"  VALUE           -108096
 *     27   11 SET    "g"  VALUE   OLD     -108096   TO           2   NEW           2
 *     28   12 MUL    "g"  VALUE   OLD           2   *            4   NEW           8
 *     29   13 SUB    "g"  VALUE   OLD           8   +       108100   NEW     -108092
 *     30   14 JNZ 1  "g"  VALUE           -108092
 *     31   16 SUB    "e"  VALUE   OLD           4   +           -1   NEW           5
 *     32   17 SET    "g"  VALUE   OLD     -108092   TO           5   NEW           5
 *     33   18 SUB    "g"  VALUE   OLD           5   +       108100   NEW     -108095
 *     34   19 JNZ 1  "g"  VALUE           -108095
 *     35   11 SET    "g"  VALUE   OLD     -108095   TO           2   NEW           2
 *     36   12 MUL    "g"  VALUE   OLD           2   *            5   NEW          10
 *     37   13 SUB    "g"  VALUE   OLD          10   +       108100   NEW     -108090
 *     38   14 JNZ 1  "g"  VALUE           -108090
 *     39   16 SUB    "e"  VALUE   OLD           5   +           -1   NEW           6
 *     40   17 SET    "g"  VALUE   OLD     -108090   TO           6   NEW           6
 *     41   18 SUB    "g"  VALUE   OLD           6   +       108100   NEW     -108094
 *     42   19 JNZ 1  "g"  VALUE           -108094
 *     43   11 SET    "g"  VALUE   OLD     -108094   TO           2   NEW           2
 *     44   12 MUL    "g"  VALUE   OLD           2   *            6   NEW          12
 *     45   13 SUB    "g"  VALUE   OLD          12   +       108100   NEW     -108088
 *     46   14 JNZ 1  "g"  VALUE           -108088
 *     47   16 SUB    "e"  VALUE   OLD           6   +           -1   NEW           7
 *     48   17 SET    "g"  VALUE   OLD     -108088   TO           7   NEW           7
 *     49   18 SUB    "g"  VALUE   OLD           7   +       108100   NEW     -108093
 * 
 * pgm_counter 19
 * step_counter 50
 * 
 * 
 * Register 97  a  = 0
 * Register 98  b  = 108100
 * Register 99  c  = 125100
 * Register 100  d  = 2
 * Register 101  e  = 7
 * Register 102  f  = 1
 * Register 103  g  = -108093
 * Register 104  h  = 0
 * 
 * ------------------------------------------------------------------------------------------
 * Result Part 1 6
 * Result Part 2 0
 * 
 * </pre> 
 */
public class Day23_CoprocessorConflagration
{
  public static void main( String[] args )
  {
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

    result_part_01 = run( pListInput, 0, pKnzDebug );
    //result_part_02 = run( pListInput, 1, pKnzDebug );

    wl( "" );
    wl( "------------------------------------------------------------------------------------------" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
  }

  private static long run( List< String > pListInput, long pInitValueA, boolean pKnzDebug )
  {
    if ( pKnzDebug )
    {
      wl( "" );
      wl( "------------------------------------------------------------------------------------------" );
      wl( "RUN " );
      wl( "" );
    }

    int index_op_old_value = 1;
    int index_op_mul_count = 2;

    long[] register_vector = new long[ 130 ];

    for ( int idx = 90; idx < 130; idx++ )
    {
      register_vector[ idx ] = 0l;
    }

    register_vector[ 97 ] = pInitValueA;

    int pgm_counter = 0;

    long step_counter = 0;

    while ( ( pgm_counter >= 0 ) && ( pgm_counter < pListInput.size() ) && ( step_counter < 60_000 ) )
    {
      String input_str = pListInput.get( pgm_counter );

      char char_register_op = input_str.charAt( 4 );

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

      if ( input_str.startsWith( "jnz" ) )
      {
        int old_pgm_counter = pgm_counter;

        long check_value = 0;

        if ( ( char_register_op >= 'a' ) && ( char_register_op <= 'z' ) )
        {
          check_value = register_vector[ ( (int) char_register_op ) ];
        }
        else
        {
          check_value = Long.parseLong( "" + char_register_op );
        }

        if ( check_value != 0 )
        {
          pgm_counter += ins_val;

          if ( pKnzDebug )
          {
            wl( String.format( "%6d %4d JNZ 1  \"" + char_register_op + "\"  VALUE       %11d", step_counter, old_pgm_counter, register_vector[ ( (int) char_register_op ) ] ) );
          }

        }
        else
        {
          if ( pKnzDebug )
          {
            wl( String.format( "%6d %4d JNZ 0  \"" + char_register_op + "\"  VALUE       %11d", step_counter, old_pgm_counter, register_vector[ ( (int) char_register_op ) ] ) );
          }

          pgm_counter++;
        }
      }
      else
      {
        if ( input_str.startsWith( "set" ) )
        {
          register_vector[ ( (int) char_register_op ) ] = ins_val;

          if ( pKnzDebug )
          {
            wl( String.format( "%6d %4d SET    \"" + char_register_op + "\"  VALUE   OLD %11d   TO %11d   NEW %11d", step_counter, pgm_counter, register_vector[ index_op_old_value ], ins_val, register_vector[ ( (int) char_register_op ) ] ) );
          }
        }
        else if ( input_str.startsWith( "sub" ) )
        {
          register_vector[ ( (int) char_register_op ) ] -= ins_val;

          if ( pKnzDebug )
          {
            wl( String.format( "%6d %4d SUB    \"" + char_register_op + "\"  VALUE   OLD %11d   +  %11d   NEW %11d", step_counter, pgm_counter, register_vector[ index_op_old_value ], ins_val, register_vector[ ( (int) char_register_op ) ] ) );
          }
        }
        else if ( input_str.startsWith( "mul" ) )
        {
          register_vector[ ( (int) char_register_op ) ] *= ins_val;

          register_vector[ index_op_mul_count ]++;

          if ( pKnzDebug )
          {
            wl( String.format( "%6d %4d MUL    \"" + char_register_op + "\"  VALUE   OLD %11d   *  %11d   NEW %11d", step_counter, pgm_counter, register_vector[ index_op_old_value ], ins_val, register_vector[ ( (int) char_register_op ) ] ) );
          }
        }

        pgm_counter++;
      }

      step_counter++;
    }

    wl( "" );
    wl( "pgm_counter  " + pgm_counter );
    wl( "step_counter " + step_counter );
    wl( "" );
    wl( "" );

    for ( int idx = 97; idx < 105; idx++ )
    {
      wl( "Register " + idx + "  " + ( (char) idx ) + "  = " + register_vector[ idx ] );
    }

    return register_vector[ index_op_mul_count ];
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2017__day23_input.txt";

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