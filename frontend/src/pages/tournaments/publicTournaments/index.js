import React, {useState, useCallback, useEffect} from "react";
import Card from 'react-bootstrap/Card';
import Container from 'react-bootstrap/Container';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
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
            show: 'Lang',
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
            setShow(false);
            getData(1, pageSize);
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
                                <h3>
                                    NO PUBLIC TOURNAMENTS
                                </h3>
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