
import './App.css';
import Headers from './components/Header.jsx';
import "bootstrap/dist/css/bootstrap.min.css";
import { Outlet } from "react-router-dom";
function App() {
  return (
    <div className="App">
      <div className='app-header'>
        <Headers />

      </div>
      <div className='app-content'>
        <Outlet />
      </div>
    </div>
  );
}

export default App;
