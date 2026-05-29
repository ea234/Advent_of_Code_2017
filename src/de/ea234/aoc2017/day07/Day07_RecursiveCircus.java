package de.ea234.aoc2017.day07;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * <pre>
 * --- Day 8: I Heard You Like Registers ---
 * https://adventofcode.com/2017/day/7
 * 
 * https://www.reddit.com/r/adventofcode/comments/7i44pg/2017_day_7_solutions/
 * 
 * PGM name pbga     weight  66
 * PGM name xhth     weight  57
 * PGM name ebii     weight  61
 * PGM name havc     weight  66
 * PGM name ktlj     weight  57
 * PGM name fwft     weight  72
 * PGM name qoyq     weight  66
 * PGM name padx     weight  45
 * PGM name tknk     weight  41
 * PGM name jptl     weight  61
 * PGM name ugml     weight  68
 * PGM name gyxo     weight  61
 * PGM name cntj     weight  57
 * 
 * 
 * PGM name fwft     ->  ktlj, cntj, xhth
 * PGM name padx     ->  pbga, havc, qoyq
 * PGM name tknk     ->  ugml, padx, fwft
 * PGM name ugml     ->  gyxo, ebii, jptl
 * 
 * 
 * PGM name tknk     input -         
 * PGM name qoyq     input padx      
 * PGM name ugml     input tknk      
 * PGM name fwft     input tknk      
 * PGM name jptl     input ugml      
 * PGM name cntj     input fwft      
 * PGM name padx     input tknk      
 * PGM name ebii     input ugml      
 * PGM name ktlj     input fwft      
 * PGM name havc     input padx      
 * PGM name pbga     input padx      
 * PGM name gyxo     input ugml      
 * PGM name xhth     input fwft      
 * 
 * 
 * PGM name tknk             weight     41  x-weight    778  -    8 
 * PGM name qoyq             weight     66  x-weight     66  -     
 * PGM name ugml             weight     68  x-weight    251  -     
 * PGM name fwft             weight     72  x-weight    243  -     
 * PGM name jptl             weight     61  x-weight     61  -     
 * PGM name cntj             weight     57  x-weight     57  -     
 * PGM name padx             weight     45  x-weight    243  -     
 * PGM name ebii             weight     61  x-weight     61  -     
 * PGM name ktlj             weight     57  x-weight     57  -     
 * PGM name havc             weight     66  x-weight     66  -     
 * PGM name pbga             weight     66  x-weight     66  -     
 * PGM name gyxo             weight     61  x-weight     61  -     
 * PGM name xhth             weight     57  x-weight     57  -     
 * 
 * 
 * PGM tknk
 *   - PGM name ugml             weight     68  x-weight    251  -     
 *   - PGM name padx             weight     45  x-weight    243  -     
 *   - PGM name fwft             weight     72  x-weight    243  -  
 * 
 * Result Part 1 tknk
 * Result Part 2 8
 *
 * 
 * Result Part 1 ykpsek
 * Result Part 2 9
 * 
 * 
 * ------------------------------------------------------------------
 * 
 * PGM ykpsek
 *   - PGM name xgudb            weight  26966  x-weight  94250  -     
 *   - PGM name fucsb            weight  24047  x-weight  94250  -     
 *   - PGM name rsalq            weight  47820  x-weight  94259  -    9 
 *   - PGM name xjllex           weight  64931  x-weight  94250  -     
 *   - PGM name splbrdu          weight     58  x-weight  94250  -     
 * 
 * 
 * PGM rsalq
 *   - PGM name sdxbol           weight   2587  x-weight   9286  -     
 *   - PGM name irqwcab          weight   8461  x-weight   9286  -     
 *   - PGM name jiaiwto          weight    936  x-weight   9286  -     
 *   - PGM name viydtj           weight     10  x-weight   9286  -     
 *   - PGM name uduyfo           weight   1710  x-weight   9295  -    9 
 * 
 * 
 * PGM uduyfo
 *   - PGM name amhaz            weight     39  x-weight   1894  -     
 *   - PGM name cumah            weight   1069  x-weight   1903  -     
 *   - PGM name zqfvypo          weight   1810  x-weight   1894  -     
 *   - PGM name dribos           weight   1213  x-weight   1894  -     
 * 
 * Result Part 1 ykpsek
 * Result Part 2 9
 * 
 * 
 * </pre>
 */
public class Day07_RecursiveCircus
{
  public static void main( String[] args )
  {
    String test_input = "pbga (66);xhth (57);ebii (61);havc (66);ktlj (57);fwft (72) -> ktlj, cntj, xhth;qoyq (66);padx (45) -> pbga, havc, qoyq;tknk (41) -> ugml, padx, fwft;jptl (61);ugml (68) -> gyxo, ebii, jptl;gyxo (61);cntj (57)";

    calculatePart01( test_input, true );

    calculate01( getListProd(), false );

    System.exit( 0 );
  }

  private static void calculatePart01( String pString, boolean pKnzDebug )
  {
    List< String > converted_string_list = Arrays.stream( pString.split( ";" ) ).map( String::trim ).collect( Collectors.toList() );

    calculate01( converted_string_list, pKnzDebug );
  }

  private static void calculate01( List< String > pListInput, boolean pKnzDebug )
  {
    String result_part_01 = "";

    int result_part_02 = 0;

    HashMap< String, Day07Pgm > pgm_map = new HashMap< String, Day07Pgm >();

    Properties prop_names = new Properties();

    for ( String input_str : pListInput )
    {
      if ( !input_str.isBlank() )
      {
        int index_bracket_open = input_str.indexOf( "(" );

        int index_bracket_close = input_str.indexOf( ")" );

        String pgm_name = input_str.substring( 0, index_bracket_open ).trim();

        String pgm_weight = input_str.substring( index_bracket_open + 1, index_bracket_close ).trim();

        prop_names.setProperty( pgm_name, "-" );

        pgm_map.put( pgm_name, new Day07Pgm( pgm_name, pgm_weight ) );

        if ( pKnzDebug )
        {
          wl( String.format( "PGM name %-6s   weight %3s", pgm_name, pgm_weight ) );
        }
      }
    }

    if ( pKnzDebug )
    {
      wl( "" );
    }

    for ( String input_str : pListInput )
    {
      if ( !input_str.isBlank() )
      {
        int index_arrow = input_str.indexOf( "->" );

        if ( index_arrow > 0 )
        {
          int index_bracket_open = input_str.indexOf( "(" );

          String pgm_name = input_str.substring( 0, index_bracket_open ).trim();

          String pgm_above = input_str.substring( index_arrow + 2 ).trim();

          List< String > pgm_names = Arrays.stream( pgm_above.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

          for ( String pgm_name_above : pgm_names )
          {
            prop_names.setProperty( pgm_name_above, pgm_name );
          }

          Day07Pgm cur_pgm = pgm_map.get( pgm_name );

          cur_pgm.setPgmsAbove( pgm_names );

          if ( pKnzDebug )
          {
            wl( String.format( "PGM name %-6s   ->  %s", pgm_name, pgm_above ) );
          }
        }
      }
    }

    if ( pKnzDebug )
    {
      wl( "" );
    }

    Enumeration enumeration_keys = prop_names.keys();

    String property_key = null;

    String property_value = null;

    while ( enumeration_keys.hasMoreElements() )
    {
      property_key = (String) enumeration_keys.nextElement();

      property_value = prop_names.getProperty( property_key );

      if ( property_value != null )
      {
        if ( property_value.length() == 1 )
        {
          result_part_01 = property_key;
        }

        if ( pKnzDebug )
        {
          wl( String.format( "PGM name %-6s   input %-10s", property_key, property_value ) );
        }
      }
    }

    wl( "" );
    wl( "" );

    result_part_02 = -1;

    Day07Pgm cur_pgm = pgm_map.get( result_part_01 );

    cur_pgm.calcWeight( pgm_map );

    for ( Map.Entry< String, Day07Pgm > entry : pgm_map.entrySet() )
    {
      String key = entry.getKey();

      Day07Pgm value = entry.getValue();

      int balance_weight = value.checkBalance( pgm_map );

      if ( balance_weight > result_part_02 )
      {
        result_part_02 = balance_weight;
      }

      if ( pKnzDebug )
      {
        wl( value.toString() );
      }
    }

    wl( "" );
    wl( "" );

    wl( cur_pgm.toStringBalance( pgm_map ) );

    cur_pgm = pgm_map.get( "rsalq" );

    if ( cur_pgm != null )
    {
      wl( "" );
      wl( "" );

      wl( cur_pgm.toStringBalance( pgm_map ) );

      cur_pgm = pgm_map.get( "uduyfo" );

      if ( cur_pgm != null )
      {
        wl( "" );
        wl( "" );

        wl( cur_pgm.toStringBalance( pgm_map ) );
      }
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

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2017__day07_input.txt";

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