import { useState } from 'react'
import SidebarMenu from '../src/menu/menu'
import Login from '../src/login/login'
import Horarios from '../src/horario/horario'
import 'bootstrap/dist/css/bootstrap.min.css';
import '@fortawesome/fontawesome-free/css/all.min.css';
import './App.css';

function App() {

  return (
    <div className="app-container">
        <Login />
    </div>
);
}

export default App
