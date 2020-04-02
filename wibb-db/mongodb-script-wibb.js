conn = new Mongo();
db = conn.getDB("wibbdb");

db.beers.drop();

db.beers.insert({ name: "Stiegl", icon: "/res/img/beer/stiegl.png", iconMeta: { iconBg: "#ddf6ea" } });
db.beers.insert({ name: "Gösser", icon: "/res/img/beer/goesser.png", iconMeta: { iconBg: "#296e3b" } });
db.beers.insert({ name: "Hirter", icon: "/res/img/beer/hirter.png", iconMeta: { iconBg: "#281b81" } });
db.beers.insert({ name: "Villacher", icon: "/res/img/beer/villacher.png", iconMeta: { iconBg: "#FFFFFF" } });
db.beers.insert({ name: "Puntigamer", icon: "/res/img/beer/puntigamer.png", iconMeta: { iconBg: "#0027af" } });
db.beers.insert({ name: "Murauer", icon: "/res/img/beer/murauer.png", iconMeta: { iconBg: "#ffff00" } });
db.beers.insert({ name: "Zipfer", icon: "/res/img/beer/zipfer.png", iconMeta: { iconBg: "#cacaca" } });
db.beers.insert({ name: "Wieselburger", icon: "/res/img/beer/wieselburger.png", iconMeta: { iconBg: "#fef7e2" } });
db.beers.insert({ name: "Egger", icon: "/res/img/beer/egger.png", iconMeta: { iconBg: "#2f593a" } });

db.stores.drop();

db.stores.insert({ name: "Adeg", icon: "/res/img/store/adeg.png", iconMeta: { iconBg: "#60c037" } });
db.stores.insert({ name: "Billa", icon: "/res/img/store/billa.png", iconMeta: { iconBg: "#ff3c19" } });
db.stores.insert({ name: "Hofer", icon: "/res/img/store/hofer.png", iconMeta: { iconBg: "#00136b" } });
db.stores.insert({ name: "Merkur", icon: "/res/img/store/merkur.png", iconMeta: { iconBg: "#37b678" } });
db.stores.insert({ name: "MPreis", icon: "/res/img/store/mpreis.png", iconMeta: { iconBg: "#ff0800" } });
db.stores.insert({ name: "Spar", icon: "/res/img/store/spar.png", iconMeta: { iconBg: "#FFFFFF" } });
db.stores.insert({ name: "Eurospar", icon: "/res/img/store/eurospar.png", iconMeta: { iconBg: "#FFFFFF" } });
db.stores.insert({ name: "Interspar", icon: "/res/img/store/interspar.png", iconMeta: { iconBg: "#FFFFFF" } });
db.stores.insert({ name: "T&G", icon: "/res/img/store/tundg.png", iconMeta: { iconBg: "#2b60a9" } });

db.offers.drop();
