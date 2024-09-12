import 'bootstrap/dist/css/bootstrap.min.css';
import React, { useEffect, useState } from 'react';
import { Button, Card, Container, Form, ListGroup, Modal } from 'react-bootstrap';
import { getRule, updateRule } from '../api/api';
import './Regulations.css';

const Regulations = () => {
    const [rules, setRules] = useState(new Map());
    const [showModal, setShowModal] = useState(false);
    const [editableRules, setEditableRules] = useState({});

    useEffect(() => {
        const fetchData = async () => {
            try {
                const ruleMap = await getRule();
                setRules(ruleMap);
                setEditableRules(Object.fromEntries(ruleMap));
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData();
    }, []);

    const handleShowModal = () => setShowModal(true);
    const handleCloseModal = () => setShowModal(false);

    const handleChange = (event) => {
        const { name, value } = event.target;
        // Lưu giá trị nhập vào ô dạng văn bản
        setEditableRules({
            ...editableRules,
            [name]: value
        });
    };

    const handleSave = async () => {
        try {
            // Không cần chuyển đổi giá trị chuỗi cho `rankingOrderDetail`
            const updatedRules = {
                ...editableRules
            };
            console.log(updatedRules);
            await updateRule(updatedRules);
            setRules(new Map(Object.entries(updatedRules)));
            handleCloseModal();
            alert('Cập nhật quy định thành công!');
        } catch (error) {
            console.error('Error updating rules:', error);
            alert('Cập nhật quy định thất bại.');
        }
    };

    return (
        <Container className="regulations-container">
            <h1 className="text-center mb-4">Quy định Giải đấu</h1>
            {rules.size > 0 && (
                <Card className="mb-3">
                    <Card.Header>Quy định về cầu thủ</Card.Header>
                    <ListGroup variant="flush">
                        <ListGroup.Item>Độ tuổi tối thiểu: {rules.get('minAge')}</ListGroup.Item>
                        <ListGroup.Item>Độ tuổi tối đa: {rules.get('maxAge')}</ListGroup.Item>
                    </ListGroup>
                    <Card.Header>Quy định về đội bóng</Card.Header>
                    <ListGroup variant="flush">
                        <ListGroup.Item>Số cầu thủ tối thiểu: {rules.get('minNumOfPlayers')}</ListGroup.Item>
                        <ListGroup.Item>Số cầu thủ tối đa: {rules.get('maxNumOfPlayers')}</ListGroup.Item>
                        <ListGroup.Item>Số lượng cầu thủ nước ngoài tối đa: {rules.get('maxNumOfForeigners')}</ListGroup.Item>
                    </ListGroup>
                    <Card.Header>Quy định về trận đấu</Card.Header>
                    <ListGroup variant="flush">
                        <ListGroup.Item>Thời gian tối đa để ghi bàn: {rules.get('maxTimeToScore')} phút</ListGroup.Item>
                        <ListGroup.Item>Số loại điểm ghi bàn: {rules.get('numOfTypeScore')}</ListGroup.Item>
                        <ListGroup.Item>Điểm cho trận thắng: {rules.get('winScore')}</ListGroup.Item>
                        <ListGroup.Item>Điểm cho trận thua: {rules.get('loseScore')}</ListGroup.Item>
                        <ListGroup.Item>Điểm cho trận hòa: {rules.get('drawnScore')}</ListGroup.Item>
                    </ListGroup>
                </Card>
            )}

            <Button variant="primary" onClick={handleShowModal} className="mt-3">
                Chỉnh sửa
            </Button>

            <Modal show={showModal} onHide={handleCloseModal}>
                <Modal.Header closeButton>
                    <Modal.Title>Chỉnh sửa Quy định Giải đấu</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group controlId="minAge">
                            <Form.Label>Độ tuổi tối thiểu</Form.Label>
                            <Form.Control
                                type="number"
                                name="minAge"
                                value={editableRules.minAge}
                                onChange={handleChange}
                            />
                        </Form.Group>
                        <Form.Group controlId="maxAge">
                            <Form.Label>Độ tuổi tối đa</Form.Label>
                            <Form.Control
                                type="number"
                                name="maxAge"
                                value={editableRules.maxAge}
                                onChange={handleChange}
                            />
                        </Form.Group>
                        <Form.Group controlId="minNumOfPlayers">
                            <Form.Label>Số cầu thủ tối thiểu</Form.Label>
                            <Form.Control
                                type="number"
                                name="minNumOfPlayers"
                                value={editableRules.minNumOfPlayers}
                                onChange={handleChange}
                            />
                        </Form.Group>
                        <Form.Group controlId="maxNumOfPlayers">
                            <Form.Label>Số cầu thủ tối đa</Form.Label>
                            <Form.Control
                                type="number"
                                name="maxNumOfPlayers"
                                value={editableRules.maxNumOfPlayers}
                                onChange={handleChange}
                            />
                        </Form.Group>
                        <Form.Group controlId="maxNumOfForeigners">
                            <Form.Label>Số lượng cầu thủ nước ngoài tối đa</Form.Label>
                            <Form.Control
                                type="number"
                                name="maxNumOfForeigners"
                                value={editableRules.maxNumOfForeigners}
                                onChange={handleChange}
                            />
                        </Form.Group>
                        <Form.Group controlId="maxTimeToScore">
                            <Form.Label>Thời gian tối đa để ghi bàn</Form.Label>
                            <Form.Control
                                type="number"
                                name="maxTimeToScore"
                                value={editableRules.maxTimeToScore}
                                onChange={handleChange}
                            />
                        </Form.Group>
                        <Form.Group controlId="numOfTypeScore">
                            <Form.Label>Số loại điểm ghi bàn</Form.Label>
                            <Form.Control
                                type="number"
                                name="numOfTypeScore"
                                value={editableRules.numOfTypeScore}
                                onChange={handleChange}
                            />
                        </Form.Group>
                        <Form.Group controlId="winScore">
                            <Form.Label>Điểm cho trận thắng</Form.Label>
                            <Form.Control
                                type="number"
                                name="winScore"
                                value={editableRules.winScore}
                                onChange={handleChange}
                            />
                        </Form.Group>
                        <Form.Group controlId="loseScore">
                            <Form.Label>Điểm cho trận thua</Form.Label>
                            <Form.Control
                                type="number"
                                name="loseScore"
                                value={editableRules.loseScore}
                                onChange={handleChange}
                            />
                        </Form.Group>
                        <Form.Group controlId="drawnScore">
                            <Form.Label>Điểm cho trận hòa</Form.Label>
                            <Form.Control
                                type="number"
                                name="drawnScore"
                                value={editableRules.drawnScore}
                                onChange={handleChange}
                            />
                        </Form.Group>
                        <Form.Group controlId="rankingOrderDetail">
                            <Form.Label>Thứ tự ưu tiên xếp hạng</Form.Label>
                            <Form.Control
                                as="select"
                                name="rankingOrderDetail"
                                value={editableRules.rankingOrderDetail || ''}
                                onChange={handleChange}
                                >
                                      
                            <option value="DEFAULT">Mặc định</option>
                            <option value="ALTERNATE">Thay thế</option>
                                </Form.Control>

                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleCloseModal}>
                        Hủy
                    </Button>
                    <Button variant="primary" onClick={handleSave}>
                        Lưu
                    </Button>
                </Modal.Footer>
            </Modal>
        </Container>
    );
};

export default Regulations;
