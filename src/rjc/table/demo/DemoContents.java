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

package rjc.table.demo;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import rjc.table.Status;
import rjc.table.Status.Level;
import rjc.table.Utils;

/*************************************************************************************************/
/****************************** Contents of demo application window ******************************/
/*************************************************************************************************/

public class DemoContents extends GridPane
{
  private Status m_status = new Status(); // status associated with status bar

  /**************************************** constructor ******************************************/
  public DemoContents()
  {
    // create demo window layout
    add( getMenuBar(), 0, 0 );

    var tabs = getTabs();
    add( tabs, 0, 1 );
    setHgrow( tabs, Priority.ALWAYS );
    setVgrow( tabs, Priority.ALWAYS );

    add( getStatusBar(), 0, 2 );
  }

  /***************************************** getMenuBar ******************************************/
  private Node getMenuBar()
  {
    // create menu bar
    MenuBar menuBar = new MenuBar();

    // create help menu
    Menu help = new Menu( "Help" );
    MenuItem about = new MenuItem( "About JTableFX2 ..." );
    about.setOnAction( event -> Utils.trace( "About JTableFX2 ..." ) );
    help.getItems().addAll( about );

    menuBar.getMenus().addAll( help );
    return menuBar;
  }

  /******************************************* getTabs *******************************************/
  private Node getTabs()
  {
    // create demo tab pane
    TabPane tabs = new TabPane();

    // when selected tab changes, request focus for the newly selected tab contents
    tabs.getSelectionModel().selectedItemProperty().addListener(
        ( observable, oldTab, newTab ) -> Platform.runLater( () -> ( newTab.getContent() ).requestFocus() ) );

    return tabs;
  }

  /**************************************** getStatusBar *****************************************/
  private TextField getStatusBar()
  {
    // create status-bar for displaying status messages
    TextField statusBar = new TextField();
    statusBar.setFocusTraversable( false );
    statusBar.setEditable( false );

    // display status changes on status-bar using runLater so can handle signals from other threads
    m_status.addListener( x ->
    {
      Platform.runLater( () ->
      {
        statusBar.setText( m_status.getMessage() );
        statusBar.setStyle( m_status.getStyle() );
      } );
    } );
    m_status.update( Level.NORMAL, "Started" );

    return statusBar;
  }
}
