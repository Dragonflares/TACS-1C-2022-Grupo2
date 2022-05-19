import React, {useState, useCallback, useEffect} from "react";
import Card from 'react-bootstrap/Card';
import Container from 'react-bootstrap/Container';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';
import Modal from "react-bootstrap/Modal";
import { PaginatedTable } from "../../../shared/components/paginatedTable";
import { addParticipants, getPublicTournaments } from "../../../services/tournamentService";

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

    const getData = async (page, pageSize) =>  {
        

        //const offset = ((page - 1) * pageSize);
        //page = 1
        getPublicTournaments(1, pageSize).then(
            response => {
                if(response.status === 200){
                    setData({
                        elements: response.data.response,
                        count: 100,
                    });
                }
            }
        );
        
        /* MOCK
const elements = [];
        for(let i = offset + 1; i < offset + pageSize + 1; i++){
            elements.push({
                id: i,
                name: `tourn nr0 ${i}`,
                owner: 'pepe',
                language: 'english',
                ongoing: 'Si'
            });
        }

        const mock = {
            elements : elements,
            count : 100,
        }

        setData(mock);
        */
    };

    useEffect(() => {
        const init = async () => {
            await getData(1, pageSize);
        };

        init();
    }, []);

    const handlePageChange = useCallback(async (page, pageSize) => {
        await getData(page, pageSize);
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
        addParticipants(selected.id).then(
            response => {
                if(response.status === 200){
                    setSelected( {
                        id: 0,
                        name: ''
                    });
                }
                setShow(false);
            }
        )
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
        </>
    )
}

export default PublicTournaments;