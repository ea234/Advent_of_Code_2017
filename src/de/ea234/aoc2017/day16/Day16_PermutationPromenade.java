package de.ea234.aoc2017.day16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <pre>
 * --- Day 16: Permutation Promenade ---
 * https://adventofcode.com/2017/day/16
 * 
 * https://www.reddit.com/r/adventofcode/comments/7k572l/2017_day_16_solutions/
 * 
 * </pre>
 */
public class Day16_PermutationPromenade
{
  public static void main( String[] args )
  {
    String test_input = "s1,x3/4,pe/b";

    calculatePart01( test_input, 1, true );

    calculatePart01( getListProd(), 1, false );

    //calculatePart01( getListProd(), 1_000_000_000, false );

    System.exit( 0 );
  }

  private static void calculatePart01( String pString, int pRepeat, boolean pKnzDebug )
  {
    List< String > converted_string_list = Arrays.stream( pString.split( "," ) ).map( String::trim ).collect( Collectors.toList() );

    calculate01( converted_string_list, pRepeat, pKnzDebug );
  }

  private static void calculate01( List< String > pListInput, int pRepeat, boolean pKnzDebug )
  {
    int result_part_01 = 0;

    int result_part_02 = 0;

    char[] pos_v = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p' };

    int repeat_x_times = ( pRepeat <= 0 ? 1 : pRepeat );

    for ( int repeat_count = 0; repeat_count < repeat_x_times; repeat_count++ )
    {
      for ( String input_str : pListInput )
      {
        if ( !input_str.isBlank() )
        {
          if ( input_str.charAt( 0 ) == 's' )
          {
            int programm_count = Integer.parseInt( input_str.substring( 1 ).trim() );

            int index_source = 16 - programm_count;

            int index_target = 0;

            char[] pos_v_kopie = pos_v.clone();

            int index_insert = 0;

            while ( index_insert < 16 )
            {
              pos_v[ index_insert ] = pos_v_kopie[ index_source ];

              index_source++;

              if ( index_source == pos_v.length )
              {
                index_source = 0;
              }

              index_insert++;
            }

            if ( pKnzDebug )
            {
              wl( "Spin " + programm_count + "   " + Arrays.toString( pos_v ) );
            }
          }
          else if ( input_str.charAt( 0 ) == 'x' )
          {
            // Exchange, written xA/B, makes the programs at positions A and B swap places.

            int programm_position_a = Integer.parseInt( getString( input_str, "x", "/" ) );

            int programm_position_b = Integer.parseInt( right( input_str, "/" ).trim() );

            char x_temp = pos_v[ programm_position_a ];

            pos_v[ programm_position_a ] = pos_v[ programm_position_b ];

            pos_v[ programm_position_b ] = x_temp;

            if ( pKnzDebug )
            {
              wl( "Exchange " + programm_position_a + " - " + programm_position_b + "   " + Arrays.toString( pos_v ) );
            }
          }
          else if ( input_str.charAt( 0 ) == 'p' )
          {
            char programm_name_a = getString( input_str, "p", "/" ).charAt( 0 );

            char programm_name_b = right( input_str, "/" ).trim().charAt( 0 );

            int programm_position_a = getProgrammNameIndex( pos_v, programm_name_a );

            if ( programm_position_a == -1 )
            {
              programm_position_a = getProgrammNameIndex( pos_v, programm_name_a );
            }

            int programm_position_b = getProgrammNameIndex( pos_v, programm_name_b );

            if ( programm_position_b == -1 )
            {
              programm_position_b = getProgrammNameIndex( pos_v, programm_name_b );
            }

            char x_temp = pos_v[ programm_position_a ];

            pos_v[ programm_position_a ] = pos_v[ programm_position_b ];

            pos_v[ programm_position_b ] = x_temp;

            if ( pKnzDebug )
            {
              wl( "Partner " + programm_name_a + " - " + programm_name_b + "   " + Arrays.toString( pos_v ) );
            }
          }
          else
          {
            wl( "Unknown " + input_str );
          }
        }
      }
    }

    wl( "" );
    wl( "Result Part 1 " + getResultString( pos_v ) );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
    wl( "" );
  }

  private static int getProgrammNameIndex( char[] pos_v, char pValue )
  {
    for ( int index = 0; index < pos_v.length; index++ )
    {
      if ( pos_v[ index ] == pValue )
      {
        return index;
      }
    }

    return -1;
  }

  private static String getResultString( char[] pos_v )
  {
    String result_string = "";

    for ( char cur_char : pos_v )
    {
      result_string += cur_char;
    }

    return result_string;
  }

  private static String getListProd()
  {
    String string_array = null;

    String datei_input = "/mnt/hd4tbb/daten/zdownload/advent_of_code_2017__day16_input.txt";

    try
    {
      string_array = Files.readString( Path.of( datei_input ) );
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

  /**
   * <pre>
   * Sucht in pString die Start- und die Endzeichenfolge und gibt den 
   * "eingeschlossenen" Teilstring zurueck. 
   * 
   * Es wird ein String zurueckgeben, wenn die Start- und Endzeichenfolge 
   * gefunden wurden und hintereinander stehen. Es erfolgt keine automatische
   * Umkehrung, wenn der Start hinter dem Ende gefunden wird.  
   * In allen anderen Faellen wird null zurueckgegeben. 
   * 
   * FkString.Mid( "1234567890", "5", "6" )                                = ""
   * FkString.Mid( "1234567890", "6", "5" )                                = null
   * FkString.Mid( "1234567890", "4", "7" )                                = "56"
   * FkString.Mid( "1234567890", "3", "8" )                                = "4567"
   * 
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", "Zwei",  "Fuenf"  )  = " Drei Vier "
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", "Fuenf", "Zwei"   )  = null
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", "Zwei",  "Neun"   )  = null
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", "Null",  "Fuenf"  )  = null
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", "Null",  "Neun"   )  = null
   * FkString.Mid( null,                              "Zwei",  "Fuenf"  )  = null
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", null,    "Fuenf"  )  = null
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", "Zwei",  null     )  = null
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", null,    null     )  = null
   * FkString.Mid( "Eins Zwei Drei Vier Fuenf Sechs", "Sechs", "Fuenf"  )  = null
   * </pre>
   * 
   * @param pString der String mit dem herauszutrennenden Teilstring
   * @param pAbString Suchzeichenfolge fuer den Start
   * @param pBisString Suchzeichenfolge fuer das Ende
   * @return den Teilstring, wenn Start und Ende gefunden wurden, sonst null
   */
  private static String getString( String pString, String pAbString, String pBisString )
  {
    /*
     * Pruefung: Sind die Parameter ungleich null?
     */
    if ( ( pString != null ) && ( pAbString != null ) && ( pBisString != null ) )
    {
      /*
       * Position der Startzeichenfolge suchen.
       */
      int ab_position = pString.indexOf( pAbString );

      /*
       * Pruefung: Startzeichenfolge gefunden?
       * Wurde die Startzeichenfolge nicht gefunden, bekommt der Aufrufer "null" zurueck.
       */
      if ( ab_position >= 0 )
      {
        /* 
         * Wurde die Startzeichenfolge gefunden, muss die Ab-Position um die 
         * Laenge der Startzeichenfolge erhoeht werden. Die Startzeichenfolge 
         * ist nicht Bestandteil der Rueckgabe.  
         */
        ab_position += pAbString.length();

        /*
         * Die Bis-Zeichenfolge wird ab der Ab-Positon gesucht.
         */
        int bis_position = pString.indexOf( pBisString, ab_position );

        /*
         * Pruefung: Bis-Zeichenfolge gefunden?
         * Wurde zwar die Startzeichenfolge gefunden, aber die Bis-Zeichenfolge 
         * nicht im Suchstring enthalten ist, bekommt der Aufrufer ebenfalls null zurueck.
         */
        if ( bis_position >= 0 )
        {
          /*
           * Rueckgabe des eingeschlossenen Strings
           */
          return pString.substring( ab_position, bis_position );
        }
      }
    }

    /*
     * Als Standardrueckgabe wird eine null-Referenz zurueckgegeben. 
     */
    return null;
  }

  /**
   * <pre>
   * Liefert von pString alle Zeichen bis zum ersten Vorkommen des Trennzeichens von rechts.
   * 
   * FkString.right( "ABC.DEF.GHI.JKL",   "." ) = JKL
   * FkString.right( "ABC.DEF.GHI.JKL", "DEF" ) = ".GHI.JKL"
   * FkString.right( "ABC.DEF.GHI.JKL",   "A" ) = "BC.DEF.GHI.JKL"
   * FkString.right( "ABC.DEF.GHI.JKL",  "KL" ) = ""
   * 
   * Trennzeichen ist null oder wird nicht gefunden = Rueckgabe Eingabestring
   * 
   * FkString.right( "ABC.DEF.GHI.JKL", "XYZ" ) = "ABC.DEF.GHI.JKL"
   * FkString.right( "ABC.DEF.GHI.JKL",  null ) = "ABC.DEF.GHI.JKL"
   *  
   * </pre>
   * 
   * @param pString der String 
   * @param pTrennzeichen das Trennzeichen (oder Trennstring)
   * @return den sich ergebenden String bis zur Fundstelle des Trennzeichens
   */
  private static String right( String pString, String pTrennzeichen )
  {
    /*
     * Es wird die Variable "pos_trennzeichen" deklariert und 
     * mit -1 vorbelegt. 
     */
    int pos_trennzeichen = -1;

    /*
     * Pruefung: Sind beide Parameter ungleich "null" ?
     * 
     * Wenn beide Parameter ungleich "null" sind, wird die 
     * letzte Position des Trennzeichens gesucht. Das 
     * Ergebnis wird in der Variablen "pos_trennzeichen"
     * gespeichert.
     */
    if ( ( pString != null ) && ( pTrennzeichen != null ) )
    {
      pos_trennzeichen = pString.lastIndexOf( pTrennzeichen );
    }

    /*
     * Wurde das Trennzeichen nicht gefunden, kann das Ergebnis nur 
     * der EingabeString sein. Ist der Eingabestring selber null 
     * erhaelt der Aufrufer "null" zurueck.
     */
    if ( pos_trennzeichen == -1 )
    {
      return pString;
    }

    /*
     * Wurde das Trennzeichen gefunden 
     */
    return pString.substring( pos_trennzeichen + pTrennzeichen.length(), pString.length() );
  }
}