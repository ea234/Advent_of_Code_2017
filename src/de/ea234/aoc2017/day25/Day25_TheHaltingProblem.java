package de.ea234.aoc2017.day25;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * <pre>
 * 
 * --- Day 25: The Halting Problem ---
 * https://adventofcode.com/2017/day/25
 * 
 * https://www.reddit.com/r/adventofcode/comments/7lzo3l/2017_day_25_solutions/
 * 
 * 
 * 
 * 
 * begin_state      A
 * diagnostic_steps 6
 * 
 * State A Value 0 Write 1 Move r Cont B    Value 1 Write 0 Move l Cont B
 * State B Value 0 Write 1 Move l Cont A    Value 1 Write 1 Move r Cont A
 * 
 * Cur Value 0  State A  New Value 1   Cursor r       1   State B   
 * Cur Value 0  State B  New Value 1   Cursor l       0   State A   
 * Cur Value 1  State A  New Value 0   Cursor l      -1   State B   
 * Cur Value 0  State B  New Value 1   Cursor l      -2   State A   
 * Cur Value 0  State A  New Value 1   Cursor r      -1   State B   
 * Cur Value 1  State B  New Value 1   Cursor r       0   State A   
 * 
 * cursor_min    -2
 * cursor_max    1
 * 
 * ------------------------------------------------------------------------------------------
 * Result Part 1 3
 * Result Part 2 0
 * 
 * 
 * 
 * begin_state      A
 * diagnostic_steps 12368930
 * 
 * State A Value 0 Write 1 Move r Cont B    Value 1 Write 0 Move r Cont C
 * State B Value 0 Write 0 Move l Cont A    Value 1 Write 0 Move r Cont D
 * State C Value 0 Write 1 Move r Cont D    Value 1 Write 1 Move r Cont A
 * State D Value 0 Write 1 Move l Cont E    Value 1 Write 0 Move l Cont D
 * State E Value 0 Write 1 Move r Cont F    Value 1 Write 1 Move l Cont B
 * State F Value 0 Write 1 Move r Cont A    Value 1 Write 1 Move r Cont E
 * 
 * 
 * cursor_min    -3988
 * cursor_max    101
 * 
 * ------------------------------------------------------------------------------------------
 * Result Part 1 2725
 * Result Part 2 0
 * 
 * </pre> 
 */
public class Day25_TheHaltingProblem
{
  public static void main( String[] args )
  {
    String test_input = "";

    test_input += "Begin in state A.";
    test_input += ",Perform a diagnostic checksum after 6 steps.";
    test_input += ",";
    test_input += ",In state A:";
    test_input += ",  If the current value is 0:";
    test_input += ",    - Write the value 1.";
    test_input += ",    - Move one slot to the right.";
    test_input += ",    - Continue with state B.";
    test_input += ",  If the current value is 1:";
    test_input += ",    - Write the value 0.";
    test_input += ",    - Move one slot to the left.";
    test_input += ",    - Continue with state B.";
    test_input += ",";
    test_input += ",In state B:";
    test_input += ",  If the current value is 0:";
    test_input += ",    - Write the value 1.";
    test_input += ",    - Move one slot to the left.";
    test_input += ",    - Continue with state A.";
    test_input += ",  If the current value is 1:";
    test_input += ",    - Write the value 1.";
    test_input += ",    - Move one slot to the right.";
    test_input += ",    - Continue with state A.";

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

    /*
     * *******************************************************************************************************
     * Parsing the input, building the touring machine
     * *******************************************************************************************************
     */

    String cur_state = null;

    HashMap< String, Day25State > touring_machine = new HashMap< String, Day25State >();

    long diagnostic_steps = 0;

    for ( int cur_index = 0; cur_index < pListInput.size(); cur_index++ )
    {
      String input_str = pListInput.get( cur_index ).trim();

      if ( !input_str.isBlank() )
      {
        if ( input_str.startsWith( "In state " ) )
        {
          Day25State touring_state = new Day25State( pListInput, cur_index );

          touring_machine.put( touring_state.getName(), touring_state );

          cur_index += 9;
        }
        else if ( input_str.startsWith( "Begin in state" ) )
        {
          cur_state = "" + input_str.charAt( 15 );
        }
        else if ( input_str.startsWith( "Perform a diagnostic" ) )
        {
          diagnostic_steps = Long.parseLong( input_str.substring( 36, input_str.indexOf( " steps." ) ) );
        }
      }
    }

    /*
     * *******************************************************************************************************
     * Do some debug-stuff
     * *******************************************************************************************************
     */

    wl( "" );
    wl( "" );
    wl( "begin_state      " + cur_state );
    wl( "diagnostic_steps " + diagnostic_steps );
    wl( "" );

    for ( Map.Entry< String, Day25State > entry : touring_machine.entrySet() )
    {
      Day25State touring_state = entry.getValue();

      wl( touring_state.toString() );
    }

    wl( "" );

    /*
     * *******************************************************************************************************
     * Doing the loop
     * *******************************************************************************************************
     */

    Properties prop_memory = new Properties();

    int cursor_position = 0;

    int cursor_min = 0;
    int cursor_max = 0;

    for ( int cur_step_count = 0; cur_step_count < diagnostic_steps; cur_step_count++ )
    {
      /*
       * Get the current value 
       */
      int cur_prop_value = Integer.parseInt( prop_memory.getProperty( "" + cursor_position, "0" ) );

      /*
       * Get the touring state 
       */
      Day25State cur_touring_state = touring_machine.get( cur_state );

      /*
       * Get the touring action from the touring state according to the current value 
       */
      Day25Action touring_action = cur_touring_state.getAction( cur_prop_value );

      /*
       * Get the value to write
       */
      int new_prop_value = touring_action.getWriteValue();

      /*
       * Move the cursor position
       * l = left  = -1
       * r = ritht = +1
       */
      int new_cursor_position = touring_action.getMoveDirection() == 'l' ? cursor_position - 1 : cursor_position + 1;

      /*
       * Get some debug info
       */
      cursor_min = Math.min( cursor_min, new_cursor_position );
      cursor_max = Math.max( cursor_max, new_cursor_position );

      /*
       * Get the next state
       */
      String new_state = touring_action.getContinueState();

      /*
       * Write some debug-info
       */
      if ( pKnzDebug )
      {
        wl( String.format( "Cur Value %1d  State %1s  New Value %1d   Cursor %s %7d   State %s   ", cur_prop_value, cur_state, new_prop_value, touring_action.getMoveDirection(), new_cursor_position, new_state ) );
      }

      /*
       * Write the new value to the "memory"
       */
      prop_memory.setProperty( "" + cursor_position, "" + new_prop_value );

      /*
       * Update the cursor-position and state
       */
      cursor_position = new_cursor_position;

      cur_state = new_state;
    }

    /*
     * *******************************************************************************************************
     * Calculating result part 1
     * *******************************************************************************************************
     */

    for ( Map.Entry< Object, Object > entry : prop_memory.entrySet() )
    {
      String value = (String) entry.getValue();

      if ( value.equals( "1" ) )
      {
        result_part_01++;
      }
    }

    wl( "" );
    wl( "cursor_min    " + cursor_min );
    wl( "cursor_max    " + cursor_max );
    wl( "" );
    wl( "------------------------------------------------------------------------------------------" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
  }

  private static List< String > getListProd()
  {
    List< String > string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2017__day25_input.txt";

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
