package de.ea234.aoc2017.day03;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * <pre>
 * --- Day 1: Inverse Captcha ---
 * https://adventofcode.com/2017/day/3
 * 
 * https://www.reddit.com/r/adventofcode/comments/7h7ufl/2017_day_3_solutions/
 * 
 * 
 * Result Part 1 0
 * Result Part 2 0
 * 
 * 
 * Init     Row          1   Col          1   Value          0   +          1  =          1
 * Right    Row          1   Col          2   Value          1   +          1  =          2
 * Up       Row          0   Col          2   Value          2   +          1  =          3
 * Left     Row          0   Col          0   Value          3   +          2  =          5
 * Down     Row          2   Col          0   Value          5   +          2  =          7
 * Right    Row          2   Col          3   Value          7   +          3  =         10
 * Up       Row         -1   Col          3   Value         10   +          3  =         13
 * Left     Row         -1   Col         -1   Value         13   +          4  =         17
 * Down     Row          3   Col         -1   Value         17   +          4  =         21
 * Right    Row          3   Col          4   Value         21   +          5  =         26
 * Up       Row         -2   Col          4   Value         26   +          5  =         31
 * Left     Row         -2   Col         -2   Value         31   +          6  =         37
 * Down     Row          4   Col         -2   Value         37   +          6  =         43
 * Right    Row          4   Col          5   Value         43   +          7  =         50
 * Up       Row         -3   Col          5   Value         50   +          7  =         57
 * Left     Row         -3   Col         -3   Value         57   +          8  =         65
 * Down     Row          5   Col         -3   Value         65   +          8  =         73
 * Right    Row          5   Col          6   Value         73   +          9  =         82
 * Up       Row         -4   Col          6   Value         82   +          9  =         91
 * Left     Row         -4   Col         -4   Value         91   +         10  =        101
 * Down     Row          6   Col         -4   Value        101   +         10  =        111
 * Right    Row          6   Col          7   Value        111   +         11  =        122
 * Up       Row         -5   Col          7   Value        122   +         11  =        133
 * Left     Row         -5   Col         -5   Value        133   +         12  =        145
 * Down     Row          7   Col         -5   Value        145   +         12  =        157
 * Right    Row          7   Col          8   Value        157   +         13  =        170
 * Up       Row         -6   Col          8   Value        170   +         13  =        183
 * Left     Row         -6   Col         -6   Value        183   +         14  =        197
 * Down     Row          8   Col         -6   Value        197   +         14  =        211
 * Right    Row          8   Col          9   Value        211   +         15  =        226
 * Up       Row         -7   Col          9   Value        226   +         15  =        241
 * Left     Row         -7   Col         -7   Value        241   +         16  =        257
 * Down     Row          9   Col         -7   Value        257   +         16  =        273
 * Right    Row          9   Col         10   Value        273   +         17  =        290
 * Up       Row         -8   Col         10   Value        290   +         17  =        307
 * Left     Row         -8   Col         -8   Value        307   +         18  =        325
 * Down     Row         10   Col         -8   Value        325   +         18  =        343
 * Right    Row         10   Col         11   Value        343   +         19  =        362
 * Up       Row         -9   Col         11   Value        362   +         19  =        381
 * Left     Row         -9   Col         -9   Value        381   +         20  =        401
 * Down     Row         11   Col         -9   Value        401   +         20  =        421
 * Right    Row         11   Col         12   Value        421   +         21  =        442
 * Up       Row        -10   Col         12   Value        442   +         21  =        463
 * Left     Row        -10   Col        -10   Value        463   +         22  =        485
 * Down     Row         12   Col        -10   Value        485   +         22  =        507
 * Right    Row         12   Col         13   Value        507   +         23  =        530
 * Up       Row        -11   Col         13   Value        530   +         23  =        553
 * Left     Row        -11   Col        -11   Value        553   +         24  =        577
 * Down     Row         13   Col        -11   Value        577   +         24  =        601
 * Right    Row         13   Col         14   Value        601   +         25  =        626
 * Up       Row        -12   Col         14   Value        626   +         25  =        651
 * Left     Row        -12   Col        -12   Value        651   +         26  =        677
 * Down     Row         14   Col        -12   Value        677   +         26  =        703
 * Right    Row         14   Col         15   Value        703   +         27  =        730
 * Up       Row        -13   Col         15   Value        730   +         27  =        757
 * Left     Row        -13   Col        -13   Value        757   +         28  =        785
 * Down     Row         15   Col        -13   Value        785   +         28  =        813
 * Right    Row         15   Col         16   Value        813   +         29  =        842
 * Up       Row        -14   Col         16   Value        842   +         29  =        871
 * Left     Row        -14   Col        -14   Value        871   +         30  =        901
 * Down     Row         16   Col        -14   Value        901   +         30  =        931
 * Right    Row         16   Col         17   Value        931   +         31  =        962
 * Up       Row        -15   Col         17   Value        962   +         31  =        993
 * 
 *  485 484 483 482 481 480 479 478 477 476 475 474 473 472 471 470 469 468 467 466 465 464
 *  486 401   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   . 381
 *  487   . 325 324 323 322 321 320 319 318 317 316 315 314 313 312 311 310 309 308 307   .
 *  488   . 326 257   .   .   .   .   .   .   .   .   .   .   .   .   .   .   . 241 306   .
 *  489   . 327   . 197 196 195 194 193 192 191 190 189 188 187 186 185 184 183   . 305   .
 *  490   . 328   . 198 145   .   .   .   .   .   .   .   .   .   .   . 133 182   . 304   .
 *  491   . 329   . 199   . 101 100  99  98  97  96  95  94  93  92  91   . 181   . 303   .
 *  492   . 330   . 200   . 102  65  64  63  62  61  60  59  58  57  90   . 180   . 302   .
 *  493   . 331   . 201   . 103  66  37  36  35  34  33  32  31  56  89   . 179   . 301   .
 *  494   . 332   . 202   . 104  67  38  17  16  15  14  13  30  55  88   . 178   . 300   .
 *  495   . 333   . 203   . 105  68  39  18   5   4   3  12  29  54  87   . 177   . 299   .
 *  496   . 334   . 204   . 106  69  40  19   6   1   2  11  28  53  86   . 176   . 298   .
 *  497   . 335   . 205   . 107  70  41  20   7   8   9  10  27  52  85   . 175   . 297   .
 *  498   . 336   . 206   . 108  71  42  21  22  23  24  25  26  51  84   . 174   . 296   .
 *  499   . 337   . 207   . 109  72  43  44  45  46  47  48  49  50  83   . 173   . 295   .
 *  500   . 338   . 208   . 110  73  74  75  76  77  78  79  80  81  82   . 172   . 294   .
 *  501   . 339   . 209   . 111   .   .   .   .   .   .   .   .   .   . 122 171   . 293   .
 *  502   . 340   . 210 157 158 159 160 161 162 163 164 165 166 167 168 169 170   . 292   .
 *  503   . 341   . 211   .   .   .   .   .   .   .   .   .   .   .   .   .   . 226 291   .
 *  504   . 342 273 274 275 276 277 278 279 280 281 282 283 284 285 286 287 288 289 290   .
 *  505   . 343   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   .   . 362
 *  506 421 422 423 424 425 426 427 428 429 430 431 432 433 434 435 436 437 438 439 440 441
 * 
 * 
 * 17  16  15  14  13 30
 * 18   5   4   3  12 29
 * 19   6   1   2  11 28
 * 20   7   8   9  10 27
 * 21  22  23  24  25 26
 * 
 * round_nr 300
 * 
 * old_value =>361202<
 * cur_value =>361803<
 * value_x   =>361527<
 * value_x   =>361527<
 * value_x   =>R-24C302<
 * value_x   =>R1C1<
 * Value X   Row = -24, Column = 302
 * Value 1   Row = 1, Column = 1
 * Result    Row = 25, Column = 301
 * 
 * Result Part 1 326
 * Result Part 2 0
 * 
 * </pre>
 */
public class Day03_SpiralMemory
{
  private static final Pattern PATTERN = Pattern.compile( "^R(-?\\d+)C(-?\\d+)$" );

  public static void main( String[] args )
  {
    calculatePart01( "0,0,0", true );

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

    int result_part_02 = 0;

    Properties prop_mem = new Properties();

    long cur_value = 1;

    long value_add = 0;

    long grid_left = 1;
    long grid_right = 1;
    long grid_top = 1;
    long grid_bottom = 1;

    cur_value = addCoords( "Init", prop_mem, 0, 1, grid_top, grid_left, pKnzDebug );

    int round_nr = 0;

    int value_x = 361527;

    long old_value = 0;

    while ( ( round_nr < 20_000 ) && ( cur_value < 361527 ) )
    {
      /*
       * Expand Right
       */

      old_value = cur_value;

      grid_right++;

      value_add = grid_right - grid_left;

      if ( cur_value < 900 )
      {
        if ( ( round_nr < 5 ) || ( ( round_nr % 2 ) == 0 ) )
        {
          addToCol( prop_mem, cur_value, grid_bottom, grid_left, 1, value_add );
        }
      }

      cur_value = addCoords( "Right", prop_mem, cur_value, value_add, grid_bottom, grid_right, pKnzDebug );

      if ( ( old_value <= value_x ) && ( cur_value >= value_x ) )
      {
        addToCol( prop_mem, old_value, grid_bottom, grid_left, 1, value_add );

        break;
      }

      /*
       * Expand Top
       */

      old_value = cur_value;

      grid_top--;

      value_add = grid_bottom - grid_top;

      if ( cur_value < 900 )
      {
        if ( ( round_nr < 5 ) || ( ( round_nr % 2 ) == 0 ) )
        {
          addToRow( prop_mem, cur_value, grid_bottom, grid_right, -1, value_add );
        }
      }

      cur_value = addCoords( "Up", prop_mem, cur_value, value_add, grid_top, grid_right, pKnzDebug );

      if ( ( old_value <= value_x ) && ( cur_value >= value_x ) )
      {
        addToRow( prop_mem, old_value, grid_bottom, grid_right, -1, value_add );

        break;
      }

      /*
       * Expand Left
       */

      old_value = cur_value;

      grid_left--;

      value_add = grid_right - grid_left;

      if ( cur_value < 900 )
      {
        if ( ( round_nr < 5 ) || ( ( round_nr % 2 ) == 0 ) )
        {
          addToCol( prop_mem, cur_value, grid_top, grid_right, -1, value_add );
        }
      }

      cur_value = addCoords( "Left", prop_mem, cur_value, value_add, grid_top, grid_left, pKnzDebug );

      if ( ( old_value <= value_x ) && ( cur_value >= value_x ) )
      {
        addToCol( prop_mem, old_value, grid_top, grid_right, -1, value_add );

        break;
      }

      /*
       * Expand down
       */

      old_value = cur_value;

      grid_bottom++;

      value_add = grid_bottom - grid_top;

      if ( cur_value < 900 )
      {
        if ( ( round_nr < 5 ) || ( ( round_nr % 2 ) == 0 ) )
        {
          addToRow( prop_mem, cur_value, grid_top, grid_left, 1, value_add );
        }
      }

      cur_value = addCoords( "Down", prop_mem, cur_value, value_add, grid_bottom, grid_left, pKnzDebug );

      if ( ( old_value <= value_x ) && ( cur_value >= value_x ) )
      {
        addToRow( prop_mem, old_value, grid_top, grid_left, 1, value_add );

        break;
      }

      round_nr++;
    }

    wl( "" );

    //wl( getDebugGrid( prop_mem, -17, -17, 20, 20 ) );
    wl( getDebugGrid( prop_mem, -10, -10, 12, 12 ) );


    String cur_coords_vx = prop_mem.getProperty( "value_" + value_x );
    String cur_coords_v1 = prop_mem.getProperty( "value_" + 1 );

    Result coords_value_x = parse( cur_coords_vx );
    Result coords_value_1 = parse( cur_coords_v1 );

    result_part_01 = Math.abs( coords_value_x.row - coords_value_1.row ) + Math.abs( coords_value_x.column - coords_value_1.column );

    wl( "" );
    wl( "round_nr " + round_nr );
    wl( "" );
    wl( "old_value =>" + old_value + "<" );
    wl( "cur_value =>" + cur_value + "<" );
    wl( "value_x   =>" + value_x + "<" );
    wl( "value_x   =>" + value_x + "<" );
    wl( "value_x   =>" + cur_coords_vx + "<" );
    wl( "value_x   =>" + cur_coords_v1 + "<" );
    wl( "Value X   Row = " + coords_value_x.row + ", Column = " + coords_value_x.column );
    wl( "Value 1   Row = " + coords_value_1.row + ", Column = " + coords_value_1.column );
    wl( "Result    Row = " + Math.abs( coords_value_x.row - coords_value_1.row ) + ", Column = " + Math.abs( coords_value_x.column - coords_value_1.column ) );
    wl( "" );
    wl( "Result Part 1 " + result_part_01 );
    wl( "Result Part 2 " + result_part_02 );
    wl( "" );
  }

  private static long addCoords( String pDirection, Properties pProp, long pCurValue, long pValueAdd, long pCurRow, long pCurCol, boolean pKnzDebug )
  {
    long result_value = pCurValue + pValueAdd;

    if ( ( pKnzDebug ) && ( result_value < 1000 ) )
    {
      wl( String.format( "%-6s   Row %10d   Col %10d   Value %10d   + %10d  = %10d", pDirection, pCurRow, pCurCol, pCurValue, pValueAdd, result_value ) );
    }

    String cur_coords = "R" + pCurRow + "C" + pCurCol;

    pProp.setProperty( "value_" + result_value, cur_coords );
    pProp.setProperty( "coords_" + cur_coords, "" + result_value );

    return result_value;
  }

  private static String getDebugGrid( Properties pProperties, long pFromRow, long pFromCol, long pToRow, long pToCol )
  {
    String result_key = "";

    for ( int cur_row = (int) pFromRow; cur_row < pToRow; cur_row++ )
    {
      for ( int cur_col = (int) pFromCol; cur_col < pToCol; cur_col++ )
      {
        result_key += String.format( "%4s", pProperties.getProperty( "coords_" + "R" + cur_row + "C" + cur_col, "." ) );
      }

      result_key += "\n";
    }

    return result_key;
  }

  private static long addToCol( Properties pProp, long pCurValue, long pCurRow, long pCurCol, long pDeltaCol, long pDeltaCount )
  {
    for ( int step_count = 0; step_count < pDeltaCount; step_count++ )
    {
      pCurCol += pDeltaCol;

      pCurValue++;

      String cur_coords = "R" + pCurRow + "C" + pCurCol;

      pProp.setProperty( "value_" + pCurValue, cur_coords );
      pProp.setProperty( "coords_" + cur_coords, "" + pCurValue );
    }

    return pCurValue;
  }

  private static long addToRow( Properties pProp, long pCurValue, long pCurRow, long pCurCol, long pDeltaRow, long pDeltaCount )
  {
    for ( int step_count = 0; step_count < pDeltaCount; step_count++ )
    {
      pCurRow += pDeltaRow;

      pCurValue++;

      String cur_coords = "R" + pCurRow + "C" + pCurCol;

      pProp.setProperty( "value_" + pCurValue, cur_coords );
      pProp.setProperty( "coords_" + cur_coords, "" + pCurValue );
    }

    return pCurValue;
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

  private static void wl( String pString ) // wl = short for "write log"
  {
    System.out.println( pString );
  }

  public static Result parse( String pInput )
  {
    if ( pInput == null )
    {
      throw new IllegalArgumentException( "input darf nicht null sein" );
    }

    Matcher m = PATTERN.matcher( pInput.trim() );

    if ( !m.matches() )
    {
      throw new IllegalArgumentException( "Ungültiges Format. Erwartet: R<Zahl>C<Zahl>, z.B. R12C33 oder R-5C-7" );
    }

    long row = Long.parseLong( m.group( 1 ) );
    long col = Long.parseLong( m.group( 2 ) );

    return new Result( row, col );
  }

  public static class Result
  {
    public final long row;

    public final long column;

    public Result( long row, long column )
    {
      this.row = row;
      this.column = column;
    }
  }
}
