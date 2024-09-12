import React, { useState } from 'react';
import axios from 'axios';
import { Alert, Button } from 'react-bootstrap';
import './Login.css';

const AuthRegister = () => {
    const [name, setName] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');
    const [age, setAge] = useState('');

    const [access_token, setAccessToken] = useState('');
    const [showAlert, setShowAlert] = useState(false);

    const handleRegister = async () => {
        try {
            const response = await axios.post('http://localhost:8080/api/v1/auth/register', {
                name: name,
                password: password,
                email: email,
                age: age
            });
            if (response.status === 200) {
                // Xử lý khi đăng nhập thành công
                alert('Đăng nhập thành công!')
                localStorage.setItem('access_token', response.data.access_token);
                localStorage.setItem('isLogin',true);

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
    

    return (
        <>
            <div className="login-container col-12 col-sm-4">
                <div className="title">Register</div>
                <input
                    type="text"
                    placeholder="Name"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                />
                <input
                    type="text"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
                <input
                    type="password"
                    placeholder="Password..."
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <input
                    type="number"
                    placeholder="Age"
                    value={age}
                    onChange={(e) => setAge(e.target.value)}
                />

                <button onClick={handleRegister}>Register</button>
                <div className="back">Go back</div>
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

    
}

export default AuthRegister;