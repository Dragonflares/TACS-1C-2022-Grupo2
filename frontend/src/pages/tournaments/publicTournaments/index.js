import React, {useState, useCallback, useEffect} from "react";
import Card from 'react-bootstrap/Card';
import Container from 'react-bootstrap/Container';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import Image from 'react-bootstrap/Image';
import { Row } from "react-bootstrap";
import Modal from "react-bootstrap/Modal";
import { PaginatedTable } from "../../../shared/components/paginatedTable";
import { addParticipants, getPublicTournaments, getPublicTournamentsCount } from "../../../services/tournamentService";
import { getUserDataStruct } from '../../../services/userService'
import { ToastContainer, toast } from 'react-toastify';

export function PublicTournaments () {
    const pageSize = 10;
    const [data, setData] = useState({
        elements: [],
        count: 10,
    });
    const [show, setShow] = useState(false);
    const [selected, setSelected] = useState(
        {
            id: 0,
            name: ''
        }
    );

    const headings = [
        {   
            name: 'name',
            show: 'Name'
        },
        {
            name: 'language',
            show: 'Language',
        },
        {
            name: 'startDate',
            show: 'Start Date'
        },
        {
            name: 'endDate',
            show: 'End Date'
        }
    ];

    const getData = (page, pageSize) =>  {
        getPublicTournaments(page, pageSize).then(
            response => {
                console.log(response)

                setData(p => (
                    {
                        ...p,
                        elements: response.data.response,
                    }
                ))

                getPublicTournamentsCount().then(
                    resp => {
                        setData(p => ({
                            ...p,
                            count: resp.data.response.quantity,
                        }))
                    }
                ).catch( e => {
                        toast.error(e.response.data.response.message);
                    })         
            }
        ).catch( e => {
                toast.error(e.response.data.response.message);
            })
    };

    useEffect(() => {
        const init = async () => {
            getData(1, pageSize);
        };

        init();
    }, []);

    const handlePageChange = useCallback(async (page, pageSize) => {
        getData(page, pageSize);
    });

    const handleRowClick = useCallback((element) => {
        setShow(true);
        setSelected(element);
    });

    const handleHide = useCallback(() => {
        setShow(false);
        setSelected( {
            id: 0,
            name: ''
        });
    });

    const handleJoin = useCallback(() => {
        addParticipants(selected.id,getUserDataStruct()).then(
            response => {
                toast.success(`Successfully joined ${selected.name}!`);
                setSelected( {
                    id: 0,
                    name: ''
                });
            }
        ).catch(
            e => {            
                toast.error(e.response.data.response.message)
            }
        ).finally(() => {
            //REFRESH TABLE?
            setShow(false);
        });
    });

    return (
        <>
            <Col xs={12} md={{offset: 3, span: 6}}>
                <Container fluid>
                    <Card>
                        <Card.Body>
                            <Card.Title>Public Tournaments</Card.Title>
                            {
                                data.count > 0 ?
                                    <>
                                        <PaginatedTable 
                                            headings={headings}
                                            data={data}
                                            pageSize={pageSize}
                                            onPageChange={handlePageChange}
                                            onClick={handleRowClick}
                                            key='publicTournaments'
                                        />
                                    </>
                                :
                                <Container fluid>
                                <div style={{marginTop: 5 +'%'}}>
                                    <Col xs={12} md={{offset: 2, span: 8}}>
                                        <Row>
                                            <Col xs={12} md={{offset: 3, span: 6}}>
                                                <Image src='images/404.png' fluid/>
                                            </Col>
                                        </Row>
                                        <Row>
                                            <Col>
                                                <i><p>No public tournaments found.</p>
                                                <p>Try creating a new one!</p></i>
                                            </Col>
                                        </Row>
                                                   
                                    </Col>
                                </div>                
                            </Container>
                            }
                        </Card.Body>
                    </Card>
                </Container>        
            </Col>
            <Modal size="sm" show={show} onHide={handleHide} backdrop="static" centered>
                <Modal.Header>
                    <Modal.Title>Join Tournament {selected.name}</Modal.Title>
                </Modal.Header>
                <Modal.Footer>
                    <Button variant="danger"  type={'button'} onClick={handleHide}>
                        NO
                    </Button>
                    <Button variant="primary" type={'button'} onClick={handleJoin}>
                        YES
                    </Button>
                </Modal.Footer>
            </Modal>
            <ToastContainer/>
        </>
    )
}

export default PublicTournaments;