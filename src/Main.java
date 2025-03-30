public class Main {
    // entry point
    /*
     *  Engines: handles game logic, updates JPanels too, bulk of code will be here
     *  Controllers: handle events, calls Engine methods
     *  Panels: initializes game components and paints
     *  Panels <- Controllers <- Engine
     *
     *  modifying components outside actionListener requires invokeLater()
     *  use layout managers - if not possible add coordinate ratios
     *  use graphics2d for map
     *  never use paint - always use paintComponent()
     */
}
