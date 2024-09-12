import React, { useState } from 'react';
import { Button, Form, ListGroup, Modal } from 'react-bootstrap';
import { getTeam, searchPlayers, updatePlayer } from '../api/api'; // Import hàm API, including updatePlayer
import './Lookup.css'; // Import CSS file

const Lookup = () => {
    const [playerName, setPlayerName] = useState('');
    const [Teamname, setTeamname] = useState('');
    const [birthYear, setBirthYear] = useState('');
    const [playerType, setPlayerType] = useState('');
    const [players, setPlayers] = useState([]);
    const [showModal, setShowModal] = useState(false);
    const [selectedPlayer, setSelectedPlayer] = useState(null);

    const handlePlayerNameChange = (event) => {
        setPlayerName(event.target.value);
    };

    const handleTeamNameChange = (event) => {
        setTeamname(event.target.value);
    };

    const handleBirthYearChange = (event) => {
        setBirthYear(event.target.value);
    };

    const handlePlayerTypeChange = (event) => {
        setPlayerType(event.target.value);
    };

    const handleSearch = async () => {
        try {
            const t = await getTeam();
            const teamId = t.get(Teamname);
            const data = await searchPlayers(playerName, teamId, birthYear, playerType);

            // Format the players' data
            const formattedPlayers = data.map(player => ({
                id: player.id,
                name: player.name,
                dateOfBirth: player.dateOfBirth,
                type: player.type,
                note: player.note,
                team: player.team,
                totalGoals: player.totalGoals,
                tournamentSeasons: player.tournamentSeasons
            }));

            setPlayers(formattedPlayers);
        } catch (error) {
            console.error('Error searching players:', error);
        }
    };

    const handlePlayerClick = (player) => {
        setSelectedPlayer(player);
        setShowModal(true);
    };

    const handleModalClose = () => {
        setShowModal(false);
        setSelectedPlayer(null);
    };

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        setSelectedPlayer(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSaveChanges = async () => {
        try {
            if (selectedPlayer) {
                console.log(selectedPlayer.id, selectedPlayer)
                await updatePlayer(selectedPlayer); // Assuming updatePlayer is a function that takes an ID and updated player data
                setShowModal(false);
                alert('Cập nhật thành công!');
            }
        } catch (error) {
            console.error('Error updating player:', error);
            alert('Cập nhật thất bại!');
        }
    };

    return (
        <div className="player-lookup-container">
            <Form.Group className="search-group">
                <Form.Label>Tìm kiếm cầu thủ:</Form.Label>

                <Form.Control
                    type="text"
                    placeholder="Nhập tên cầu thủ..."
                    value={playerName}
                    onChange={handlePlayerNameChange}
                    className="search-input"
                />

                <Form.Control
                    type="text"
                    placeholder="Nhập tên đội bóng..."
                    value={Teamname}
                    onChange={handleTeamNameChange}
                    className="search-input"
                />

                <Form.Control
                    type="text"
                    placeholder="Nhập năm sinh..."
                    value={birthYear}
                    onChange={handleBirthYearChange}
                    className="search-input"
                />

                <Form.Control
                    as="select"
                    value={playerType}
                    onChange={handlePlayerTypeChange}
                    className="search-select"
                >
                    <option value="">Chọn loại</option>
                    <option value="FOREIGN">Cầu thủ nước ngoài</option>
                    <option value="NATIONAL">Cầu thủ trong nước</option>
                </Form.Control>

                <Button onClick={handleSearch} className="search-button">Tìm kiếm</Button>
            </Form.Group>

            <ListGroup className="player-card-container">
                {players.map(player => (
                    <ListGroup.Item
                        key={player.id}
                        onClick={() => handlePlayerClick(player)}
                        className="player-card"
                    >
                        <strong>{player.name}</strong>  {player.team}<br />
                        Loại: {player.type === "FOREIGN" ? "Cầu thủ nước ngoài" : "Cầu thủ trong nước"}<br />
                        Ngày sinh: {new Date(player.dateOfBirth).toLocaleDateString()}<br />
                        Tổng số bàn thắng: {player.totalGoals}<br />
                        Mùa giải: {player.tournamentSeasons}<br />
                        Ghi chú: {player.note}<br />
                    </ListGroup.Item>
                ))}
            </ListGroup>

            {selectedPlayer && (
                <Modal show={showModal} onHide={handleModalClose}>
                    <Modal.Header closeButton>
                        <Modal.Title>Chỉnh sửa thông tin cầu thủ</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form>
                            <Form.Group controlId="formPlayerName">
                                <Form.Label>Tên</Form.Label>
                                <Form.Control
                                    type="text"
                                    name="name"
                                    value={selectedPlayer.name}
                                    onChange={handleInputChange}
                                />
                            </Form.Group>
                            <Form.Group controlId="formDob">
                                <Form.Label>Ngày sinh</Form.Label>
                                <Form.Control
                                    type="date"
                                    name="dateOfBirth"
                                    value={new Date(selectedPlayer.dateOfBirth).toISOString().split('T')[0]}
                                    onChange={handleInputChange}
                                />
                            </Form.Group>
                            <Form.Group controlId="formPlayerType">
                                <Form.Label>Loại</Form.Label>
                                <Form.Control
                                    as="select"
                                    name="type"
                                    value={selectedPlayer.type}
                                    onChange={handleInputChange}
                                    >
                                    <option value="FOREIGN">Cầu thủ nước ngoài</option>
                                    <option value="NATIONAL">Cầu thủ trong nước</option>
                                </Form.Control>
                            </Form.Group>
                            <Form.Group controlId="formPlayerNote">
                                <Form.Label>Ghi chú</Form.Label>
                                <Form.Control
                                    type="text"
                                    name="note"
                                    value={selectedPlayer.note}
                                    onChange={handleInputChange}
                                />
                            </Form.Group>
                        </Form>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={handleModalClose}>
                            Đóng
                        </Button>
                        <Button variant="primary" onClick={handleSaveChanges}>
                            Lưu thay đổi
                        </Button>
                    </Modal.Footer>
                </Modal>
            )}
        </div>
    );
};

export default Lookup;
