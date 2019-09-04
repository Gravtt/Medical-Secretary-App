import 'package:flutter/material.dart';
//import 'package:healthy_wealthy/sideDraw.dart';
//import 'package:font_awesome_flutter/font_awesome_flutter.dart';
class DashboardPage extends StatefulWidget {
  @override
  _DashboardPageState createState() => _DashboardPageState();
}

class _DashboardPageState extends State<DashboardPage> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      //drawer: SideDraw(),
      backgroundColor: Colors.white,
      appBar: AppBar(
        title: Text(
          "Medical Secretary",

          style: TextStyle(
              color: Colors.white, fontSize: 22.0),
        ),
        centerTitle: true,
        elevation: 0.0,
        backgroundColor: Colors.lightBlueAccent,
      ),
      body: GridView(
        gridDelegate:
        SliverGridDelegateWithFixedCrossAxisCount(crossAxisCount: 2),
        children: <Widget>[
          _buildCard("APPOINTMENT", Icons.new_releases, Colors.teal, onTap: (){Navigator.of(context).pushReplacementNamed("emergency");}),
          _buildCard("DOCTORS", Icons.add, Colors.lightBlue, onTap: (){}),
          _buildCard("HOSPITALS", Icons.local_hospital, Colors.lightBlue, onTap: (){Navigator.of(context).pushNamed("home");}),
          _buildCard("RADIOLOGY", Icons.healing, Colors.teal, onTap: (){Navigator.of(context).pushNamed("alerts");}),
          //_buildCard("PATHOLOGY", FontAwesomeIcons.ambulance, Colors.teal, onTap: (){Navigator.of(context).pushNamed("ambulance");}),
          _buildCard("RESOURCE", Icons.attach_money, Colors.lightBlue, onTap: (){Navigator.of(context).pushNamed("donate");}),
//          _buildCard("Medical History", Icons.library_books, Colors.deepPurpleAccent, onTap: (){Navigator.of(context).pushNamed("history");}),
//          _buildCard("Ask A doctor", Icons.chat, Colors.indigo, onTap: (){Navigator.of(context).pushNamed("support");}),

        ],
      ),
    );
  }

  Widget _buildCard(String title, IconData icon, Color backgroundColor, {GestureTapCallback onTap}) {

    return Padding(
      padding: const EdgeInsets.all(8.0),
      child: Card(
        elevation: 4.0,
        color: backgroundColor,
        child: InkWell(
          onTap: onTap,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: <Widget>[
              Icon(icon, size: 50.0, color: Colors.white,),
              Text(title, style: TextStyle(fontSize: 16.0, color: Colors.white, fontWeight: FontWeight.bold),),
            ],
          ),
        ),
      ),
    );
  }
}
