import React, { useState } from 'react';
import axios from 'axios';
import { Alert, Button } from 'react-bootstrap';
import './Login.css';
import { useNavigate } from 'react-router-dom';

const AuthRegister = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [userName, setUserName] = useState('');
    const [access_token, setAccessToken] = useState('');
    const [isLogin, setIsLogin] = useState(false);
    const [showAlert, setShowAlert] = useState(false);

    const navigate = useNavigate(); // Khai báo useNavigate ở đây

    const handleLogin = async () => {
        try {
            const response = await axios.post('http://localhost:8080/api/v1/auth/login', {
                username: username,
                password: password
            });
            if (response.status === 200) {
                // Xử lý khi đăng nhập thành công
                alert('Đăng nhập thành công!')
                localStorage.setItem('access_token', response.data.access_token);
                localStorage.setItem('userName', response.data.user.name);
                localStorage.setItem('isLogin', true);
                navigate('/home');


                console.log('Login successful', response.data);
            } else {
                // Xử lý khi đăng nhập thất bại
                console.log('Login failed');
            }
        } catch (error) {
            alert('Đăng nhập thất bại!')
        }
    };

    const handleCloseAlert = () => setShowAlert(false);

    // Hàm điều hướng đến trang đăng ký
    const goRegisterPage = () => {
        navigate('/authregister'); // Điều hướng đến trang đăng ký
    };

    return (
        <>
            <div className="login-container col-12 col-sm-4">
                <div className="title">Login</div>
                <div className="text">Email or username</div>
                <input
                    type="text"
                    placeholder="Email or username..."
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
                <input
                    type="password"
                    placeholder="Password..."
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <button onClick={handleLogin}>Login</button>
                <div >Or</div>
                <button onClick={goRegisterPage}> Register </button>
            </div>

            {/* Alert thông báo */}
            {showAlert && (
                <div className="alert-container">
                    <Alert variant="success" onClose={handleCloseAlert} dismissible className="alert-custom">
                        <Alert.Heading>Đăng ký thành công!</Alert.Heading>
                        <div className="alert-body">
                            <div className="alert-button-container">
                                <Button className="alert-button" onClick={handleCloseAlert}>
                                    Xác nhận
                                </Button>
                            </div>
                        </div>
                    </Alert>
                </div>
            )}
        </>
    );
};

export default AuthRegister;