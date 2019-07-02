/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { EstReqCerradoDetailComponent } from 'app/entities/est-req-cerrado/est-req-cerrado-detail.component';
import { EstReqCerrado } from 'app/shared/model/est-req-cerrado.model';

describe('Component Tests', () => {
  describe('EstReqCerrado Management Detail Component', () => {
    let comp: EstReqCerradoDetailComponent;
    let fixture: ComponentFixture<EstReqCerradoDetailComponent>;
    const route = ({ data: of({ estReqCerrado: new EstReqCerrado(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EstReqCerradoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EstReqCerradoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EstReqCerradoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.estReqCerrado).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
