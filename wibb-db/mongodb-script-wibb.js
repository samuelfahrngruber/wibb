conn = new Mongo();
db = conn.getDB("wibbdb");

db.beers.drop();

db.beers.insert({ name: "Stiegl", icon: "/res/img/beer/stiegl.png" });
db.beers.insert({ name: "Gösser", icon: "/res/img/beer/goesser.png" });
db.beers.insert({ name: "Hirter", icon: "/res/img/beer/hirter.png" });
db.beers.insert({ name: "Villacher", icon: "/res/img/beer/villacher.png" });
db.beers.insert({ name: "Puntigamer", icon: "/res/img/beer/puntigamer.png" });
db.beers.insert({ name: "Murauer", icon: "/res/img/beer/murauer.png" });
db.beers.insert({ name: "Zipfer", icon: "/res/img/beer/zipfer.png" });
db.beers.insert({ name: "Wieselburger", icon: "/res/img/beer/wieselburger.png" });
db.beers.insert({ name: "Egger", icon: "/res/img/beer/egger.png" });

db.stores.drop();

db.stores.insert({ name: "Adeg", icon: "/res/img/store/adeg.png" });
db.stores.insert({ name: "Billa", icon: "/res/img/store/billa.png" });
db.stores.insert({ name: "Hofer", icon: "/res/img/store/hofer.png" });
db.stores.insert({ name: "Merkur", icon: "/res/img/store/merkur.png" });
db.stores.insert({ name: "MPreis", icon: "/res/img/store/mpreis.png" });
db.stores.insert({ name: "Spar", icon: "/res/img/store/spar.png" });
db.stores.insert({ name: "Eurospar", icon: "/res/img/store/eurospar.png" });
db.stores.insert({ name: "Interspar", icon: "/res/img/store/interspar.png" });
db.stores.insert({ name: "T&G", icon: "/res/img/store/tundg.png" });

db.offers.drop();
