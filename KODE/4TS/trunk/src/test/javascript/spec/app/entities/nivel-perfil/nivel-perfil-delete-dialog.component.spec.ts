/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { NivelPerfilDeleteDialogComponent } from 'app/entities/nivel-perfil/nivel-perfil-delete-dialog.component';
import { NivelPerfilService } from 'app/entities/nivel-perfil/nivel-perfil.service';

describe('Component Tests', () => {
  describe('NivelPerfil Management Delete Component', () => {
    let comp: NivelPerfilDeleteDialogComponent;
    let fixture: ComponentFixture<NivelPerfilDeleteDialogComponent>;
    let service: NivelPerfilService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [NivelPerfilDeleteDialogComponent]
      })
        .overrideTemplate(NivelPerfilDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NivelPerfilDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(NivelPerfilService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
