package de.ea234.aoc2017.day08;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * --- Day 8: I Heard You Like Registers ---
 * https://adventofcode.com/2017/day/8
 * 
 * https://www.reddit.com/r/adventofcode/comments/7icnff/2017_day_8_solutions/
 * 
 * 
 * Instruction    1    Condition Register a          0 >       1    ---- 
 * Instruction    2    Condition Register b          0 <       5    Modify a    from      0 by      1 to      1
 * Instruction    3    Condition Register a          1 >=      1    Modify c    from      0 by    -10 to     10
 * Instruction    4    Condition Register c         10 ==     10    Modify c    from     10 by    -20 to    -10
 * 
 * 
 * Register    1 = a          1
 * Register    2 = c        -10
 * 
 * Result Part 1 1
 * Result Part 2 10
 * 
 * 
 * 
 * 
 * Register    1 = ss      -687
 * Register    2 = a       3226
 * Register    3 = pq     -1301
 * Register    4 = e       1733
 * Register    5 = f       5221
 * Register    6 = jus     1419
 * Register    7 = uwm     1343
 * Register    8 = vpd     2031
 * Register    9 = vby     1692
 * Register   10 = l        -15
 * Register   11 = yg      -204
 * Register   12 = q       -353
 * Register   13 = qcg    -4116
 * Register   14 = ewg     1804
 * Register   15 = t        875
 * Register   16 = qos      435
 * Register   17 = zo      2455
 * Register   18 = u       1575
 * Register   19 = v       4348
 * Register   20 = tl     -5526
 * Register   21 = x       -135
 * Register   22 = txc     2146
 * Register   23 = hnd    -3016
 * Register   24 = cfa     -165
 * 
 * Result Part 1 5221
 * Result Part 2 7491
 * 
 * </pre>
 */
public class Day08_IHeardYouLikeRegisters
{
  public static void main( String[] args )
  {
    String test_input = "b inc 5 if a > 1,a inc 1 if b < 5,c dec -10 if a >= 1,c inc -20 if c == 10";

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

    int instruction_nr = 0;

    HashMap< String, Integer > registers = new HashMap< String, Integer >();

    for ( String input_str : pListInput )
    {
      if ( !input_str.isBlank() )
      {
        instruction_nr++;

        /*
         * Splitting the input into pieces.
         * 
         *   0   1    2   3  4   5   6
         * ewg dec -458 if vpd == -500
         */
        String[] instructions = input_str.split( " " );

        /*
         * Parsing the modify part of the instruction 
         */
        String modify_register = instructions[ 0 ];

        int modify_amount = Integer.parseInt( instructions[ 2 ] );

        /*
         * Parsing the condition part of the instruction
         */

        String condition_register = instructions[ 4 ];

        int condition_amount = Integer.parseInt( instructions[ 6 ] );

        /*
         * Reading the current register values from the hashmap.
         */

        int modify_register_cur_value = registers.getOrDefault( modify_register, Integer.valueOf( 0 ) ).intValue();

        int condition_register_cur_value = registers.getOrDefault( condition_register, Integer.valueOf( 0 ) ).intValue();

        /*
         * Determine, wether the condition aplies
         */

        boolean do_action = false;

             if ( instructions[ 5 ].compareTo( "<=" ) == 0 ) do_action = condition_register_cur_value <= condition_amount;
        else if ( instructions[ 5 ].compareTo( "==" ) == 0 ) do_action = condition_register_cur_value == condition_amount;
        else if ( instructions[ 5 ].compareTo( ">=" ) == 0 ) do_action = condition_register_cur_value >= condition_amount;
        else if ( instructions[ 5 ].compareTo( "!=" ) == 0 ) do_action = condition_register_cur_value != condition_amount;
        else if ( instructions[ 5 ].compareTo( "<"  ) == 0 ) do_action = condition_register_cur_value <  condition_amount;
        else if ( instructions[ 5 ].compareTo( ">"  ) == 0 ) do_action = condition_register_cur_value >  condition_amount;

        /*
         * If the condition is true, modify the register
         */

        if ( do_action )
        {
          int debug_save_value = modify_register_cur_value;

          if ( instructions[ 1 ].compareTo( "dec" ) == 0 )
          {
            modify_register_cur_value -= modify_amount;
          }
          else
          {
            modify_register_cur_value += modify_amount;
          }

          registers.put( modify_register, Integer.valueOf( modify_register_cur_value ) );

          result_part_02 = Math.max( modify_register_cur_value, result_part_02 );

          if ( pKnzDebug )
          {
            wl( String.format( "Instruction %4d    Condition Register %-4s  %6d %-2s %6d    Modify %-4s from %6d by %6d to %6d", instruction_nr, condition_register, condition_register_cur_value, instructions[ 5 ], condition_amount, modify_register, debug_save_value, modify_amount, modify_register_cur_value ) );
          }
        }
        else
        {
          if ( pKnzDebug )
          {
            wl( String.format( "Instruction %4d    Condition Register %-4s  %6d %-2s %6d    ---- ", instruction_nr, condition_register, condition_register_cur_value, instructions[ 5 ], condition_amount ) );
          }
        }
      }
    }

    wl( "" );
    wl( "" );

    int register_nr = 0;

    for ( String key : registers.keySet() )
    {
      register_nr++;

      Integer value = registers.getOrDefault( key, Integer.valueOf( 0 ) );

      if ( pKnzDebug )
      {
        wl( String.format( "Register %4d = %-4s  %6d", register_nr, key, value ) );
      }

      result_part_01 = Math.max( value.intValue(), result_part_01 );
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

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2017__day08_input.txt";

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
