/**************************************************************************
 *  Copyright (C) 2023 by Richard Crook                                   *
 *  https://github.com/dazzle50/JTableFX2                                 *
 *                                                                        *
 *  This program is free software: you can redistribute it and/or modify  *
 *  it under the terms of the GNU General Public License as published by  *
 *  the Free Software Foundation, either version 3 of the License, or     *
 *  (at your option) any later version.                                   *
 *                                                                        *
 *  This program is distributed in the hope that it will be useful,       *
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *  GNU General Public License for more details.                          *
 *                                                                        *
 *  You should have received a copy of the GNU General Public License     *
 *  along with this program.  If not, see http://www.gnu.org/licenses/    *
 **************************************************************************/

package rjc.table.view;

import rjc.table.data.TableData;
import rjc.table.signal.ObservableDouble;

/*************************************************************************************************/
/********************************** Base class for table views ***********************************/
/*************************************************************************************************/

public class TableView extends TableParent
{
  private TableData          m_data;

  protected TableCanvas      m_canvas;
  protected TableScrollBar   m_verticalScrollBar;
  protected TableScrollBar   m_horizontalScrollBar;

  protected ObservableDouble m_zoom;

  /**************************************** constructor ******************************************/
  public TableView( TableData data )
  {
    // check parameters and setup table-view
    if ( data == null )
      throw new NullPointerException( "TableData must not be null" );
    m_data = data;
  }

}
