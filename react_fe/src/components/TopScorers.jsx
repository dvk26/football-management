import React, { useState } from 'react';
import { Table, Form } from 'react-bootstrap';
import 'bootstrap/dist/css/bootstrap.min.css';
import './TopScorers.css'; // Import file CSS riêng

const TopScorers = () => {
    const [currentLeague, setCurrentLeague] = useState('');
    const [currentSeason, setCurrentSeason] = useState('');
    const [leagues, setLeagues] = useState(['League 1', 'League 2']); // Danh sách giải đấu mẫu
    const [seasons, setSeasons] = useState(['2023', '2022']); // Danh sách mùa giải mẫu
    const [scorers, setScorers] = useState([]); // Dữ liệu cầu thủ ghi bàn

    const handleSelectLeague = (event) => {
        setCurrentLeague(event.target.value);
    };

    const handleSelectSeason = (event) => {
        setCurrentSeason(event.target.value);

        // Dữ liệu mẫu cho danh sách cầu thủ ghi bàn
        if (currentLeague && event.target.value) {
            if (event.target.value === '2023') {
                setScorers([
                    { rank: 1, name: 'Player A', team: 'Team A', goals: 10, assist: 5 },
                    { rank: 2, name: 'Player B', team: 'Team B', goals: 8, assist: 3 },
                ]);
            } else if (event.target.value === '2022') {
                setScorers([
                    { rank: 1, name: 'Player C', team: 'Team C', goals: 12, assist: 1 },
                    { rank: 2, name: 'Player D', team: 'Team D', goals: 9, assist: 0 },
                ]);
            }
        }
    };

    return (
        <>
            <div className="top-scorers-container">
                <div className="top-scorers-controls">
                    <Form.Group>
                        <Form.Label>Chọn giải đấu:</Form.Label>
                        <Form.Control as="select" onChange={handleSelectLeague} value={currentLeague}>
                            <option value="">Chọn...</option>
                            {leagues.map((league) => (
                                <option key={league} value={league}>
                                    {league}
                                </option>
                            ))}
                        </Form.Control>
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Chọn mùa giải:</Form.Label>
                        <Form.Control as="select" onChange={handleSelectSeason} value={currentSeason} disabled={!currentLeague}>
                            <option value="">Chọn...</option>
                            {seasons.map((season) => (
                                <option key={season} value={season}>
                                    {season}
                                </option>
                            ))}
                        </Form.Control>
                    </Form.Group>
                </div>

                <Table striped bordered hover className="top-scorers-table">
                    <thead>
                        <tr>
                            <th>Hạng</th>
                            <th>Tên cầu thủ</th>
                            <th>Đội</th>
                            <th>Số bàn thắng</th>
                            <th>Số kiến tạo</th>
                        </tr>
                    </thead>
                    <tbody>
                        {scorers.map((scorer, index) => (
                            <tr key={index}>
                                <td>{scorer.rank}</td>
                                <td>{scorer.name}</td>
                                <td>{scorer.team}</td>
                                <td>{scorer.goals}</td>
                                <td>{scorer.assist}</td>
                            </tr>
                        ))}
                    </tbody>
                </Table>
            </div>
        </>
    );
};

export default TopScorers;
