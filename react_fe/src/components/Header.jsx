import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import React, { useState, useEffect } from 'react';
import Navbar from 'react-bootstrap/Navbar';
import logo from '../img/logo.png';
import { Link } from "react-router-dom"
const Header = () => {
    const [isLogin, setIsLogin] = useState(false);
    const [userName, setUserName] = useState('');

    // Check login status and retrieve the user's name from localStorage or context
    useEffect(() => {
        const loggedInStatus = localStorage.getItem('isLogin'); // Simulated login status
        const storedUserName = localStorage.getItem('userName'); // Simulated user name

        if (loggedInStatus === 'true') {
            setIsLogin(true);
            setUserName(storedUserName || ''); // Set username if available
        }
    }, []);

    const handleLogout = () => {
        // Clear login status and user name on logout
        localStorage.removeItem('isLogin');
        localStorage.removeItem('userName');
        setIsLogin(false);
        setUserName('');
    };
    return (
        <Navbar expand="lg" className="bg-body-tertiary">
            <Container>
                <Navbar.Brand href="/"><img
                    src={logo}
                    alt="Logo"
                    style={{ height: '40px' }} // Adjust height as needed
                /></Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    <Nav className="mx-auto">
                    {!isLogin ? (
                            <Link to="/Login" className='nav-link'>Đăng nhập</Link>
                        ) : (
                            <>
                                <span className='nav-link'>Xin chào, {userName}</span>
                                <Link to="/" className='nav-link' onClick={handleLogout}>Đăng xuất</Link>
                            </>
                        )}
                        <Link to="/home" className='nav-link'>Trang chủ</Link>
                        <Link to="/register" className='nav-link'>Đăng ký đội</Link>
                        <Link to="/schedule" className='nav-link'>Lập lịch</Link>
                        <Link to="/search" className='nav-link'>Tra cứu cầu thủ</Link>
                        <Link to="/tournament" className='nav-link'>Bảng xếp hạng</Link>
                        <Link to="/regulations" className='nav-link'>Quy định</Link>
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}

export default Header; 