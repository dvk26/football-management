// src/components/Banner.js
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import img1 from '../img/background1.jpg';
import img2 from '../img/background2.jpg';
import img3 from '../img/background3.jpg';
import './Home.css'; // Import file CSS cho Banner
const Banner = () => {
    const [currentIndex, setCurrentIndex] = useState(0);

    const images = [
        img1,
        img2,
        img3,
        // Thêm nhiều hình ảnh nếu cần
    ];

    const nextImage = () => {
        setCurrentIndex((prevIndex) => (prevIndex + 1) % images.length);
    };

    const prevImage = () => {
        setCurrentIndex((prevIndex) => (prevIndex - 1 + images.length) % images.length);
    };
    const navigate = useNavigate();

    const handleViewChange = (view) => {
        if (view === 'rankings') {
            navigate('/tournament');
        } else if (view === 'rule') {
            navigate('/regulations'); // Assuming you will add this route later
        }
    };
    return (
        <div className="banner-container">
            <div className="banner">
                <button className="nav-button prev" onClick={prevImage}>&lt;</button>
                <div className="slide" style={{ transform: `translateX(-${currentIndex * 100}%)` }}>
                    {images.map((image, index) => (
                        <div key={index} className="slide-item">
                            <img src={image} alt={`Slide ${index + 1}`} />
                        </div>
                    ))}
                </div>
                <button className="nav-button next" onClick={nextImage}>&gt;</button>
            </div>
            <div className="home-container">
                <div
                    className="home-button rule"
                    onClick={() => handleViewChange('rule')}
                >
                    Quy định giải đấu
                </div>
                <div
                    className="home-button rankings"
                    onClick={() => handleViewChange('rankings')}
                >
                    Bảng xếp hạng
                </div>
            </div>

        </div>

    );
};

export default Banner;
