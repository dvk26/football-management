import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import Home from './components/Home.jsx'
import App from './App';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter, Routes, Route } from "react-router-dom"
import Register from './components/Register.jsx';
import Schedule from './components/Schedule.jsx';
import Login from './components/Login.jsx';
import Results from './components/Results.jsx';
import Regulations from './components/Regulations.jsx';
import Tournament from './components/Tournament.jsx';
import Lookup from './components/Lookup.jsx';
import Ranking from './components/Rankings.jsx';
import TopScorers from './components/TopScorers.jsx';
import AuthRegister from './components/AuthRegister.jsx';
const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<App />}>
        <Route path="/authregister" element={<AuthRegister />} />
          <Route path="home" element={<Home />} />
          <Route path="/register" element={<Register />} />
          <Route path="/schedule" element={<Schedule />} />
          <Route path="/search" element={<Lookup />} />
          <Route path="/regulations" element={<Regulations />} />
          <Route path="/tournament" element={<Tournament />} />
          <Route path="/rankings" element={<Ranking />} />
          <Route path="/topscorers" element={<TopScorers />} />
          <Route path="/login" element={<Login />} />
          <Route index element={<Home />} />
        </Route>
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
